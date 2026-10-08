# Plano de Implementação: Protótipo de Interface Print-Flow (Kotlin + Jetpack Compose)

Este plano foca exclusivamente na **construção da interface do usuário (UI) e navegação**, utilizando dados estáticos (mock) para visualização, sem implementação de persistência (Room) ou APIs nesta fase.

## 1. Organização da Pasta `ui/`

Para garantir um código limpo e seguindo as melhores práticas, a estrutura sugerida é:

```
ui/
├── theme/                # Definição de Cores (#007DCC, #D10056, etc.), Tipografia e Tema
├── components/           # Componentes reutilizáveis (Botões, Cards, Campos de Texto)
├── navigation/           # Configuração do NavHost e Definição de Rotas (Screens)
└── screens/              # Telas organizadas por domínio
    ├── login/            # Tela de Login e Cadastro (Referência: tela1.png)
    ├── home/             # Tela Principal e Lista de Registros (Referência: tela2.png, tela3.png)
    ├── budget/           # Formulário de Novo Orçamento (Referência: tela4.png)
    ├── details/          # Detalhes do Orçamento/Pedido e Ações (Referência: tela5.png, tela6.png)
    └── common/           # Telas/Componentes de Erro, Carregamento e Estado Vazio
```

## 2. Detalhamento das Telas (Visual e Interação)

### A. Tela de Login
*   **Objetivo:** Interface de acesso ao sistema.
*   **Elementos:** Logo do Print-Flow, campos para Usuário/Email e Senha (com máscara e ícone de visibilidade), botão "Entrar".
*   **Ações de UI:** Navegação para a Home ao clicar em "Entrar".
*   **Referência Visual:** `/docs/telas/tela1.png`

### B. Tela Principal (Home)
*   **Objetivo:** Exibição da lista de orçamentos e pedidos (usando dados de exemplo).
*   **Elementos:** Barra de busca (estética), filtros de status, lista com Cards (nome do cliente, serviço, valor e badge de status), e Floating Action Button (FAB) "+".
*   **Ações de UI:** Clicar em um card navega para a tela de Detalhes; clicar no FAB navega para Novo Orçamento.
*   **Referência Visual:** `/docs/telas/tela2.png` e `/docs/telas/tela3.png`

### C. Tela de Novo Orçamento
*   **Objetivo:** Formulário de entrada de dados para simulação de orçamento.
*   **Elementos:** Campos para Nome do Cliente, Telefone, E-mail, Seletor de Serviço (Dropdown), Quantidade e Características.
*   **Ações de UI:** Botão "Calcular e Salvar" valida os campos e retorna à Home ou Detalhes (simulação).
*   **Referência Visual:** `/docs/telas/tela4.png`

### D. Tela de Detalhes do Orçamento / Pedido
*   **Objetivo:** Visualização dos dados preenchidos e botões de ação social/status.
*   **Elementos:** Resumo dos dados, valor calculado, botão "Enviar pelo WhatsApp" e botões de alteração de status.
*   **Ações de UI:** 
    *   Simular o disparo de Intent para o WhatsApp.
    *   Alternar visualmente os estados de status (apenas em memória/UI).
*   **Referência Visual:** `/docs/telas/tela5.png` e `/docs/telas/tela6.png`

## 3. Componentes Reutilizáveis
*   **PrintFlowButton:** Botão customizado com as cores da identidade visual (#D10056 / #007DCC).
*   **PrintFlowTextField:** Campo de entrada padronizado.
*   **StatusBadge:** Componente visual para identificação do status.
*   **BudgetCard:** Card estruturado para a lista da Home.

## 4. Estados de UX (Visualização)
*   **Carregamento:** Telas de "Skeleton" ou Shimmers para simular espera.
*   **Estado Vazio:** Mensagem visual "Nenhum orçamento cadastrado" para quando a lista mock estiver vazia.
*   **Feedback Visual:** Snackbars para simular confirmação de ações (ex: "Orçamento salvo").

## 5. Navegação e Fluxo de UI
*   **Navigation Compose:** Definição de rotas e transições entre telas.
*   **Mock Data:** Uso de listas estáticas de objetos Kotlin para popular as telas durante o desenvolvimento da interface.

---
*Este plano foca na fidelidade visual e na fluidez da navegação, preparando a base para futuras integrações de dados.*
