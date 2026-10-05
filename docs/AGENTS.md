# AGENTS.md — Diretrizes para Assistentes de IA

## Comandos do Projeto

* **Sincronizar o projeto**: Gradle Sync no Android Studio
* **Executar o aplicativo**: Run no Android Studio
* **Compilar o projeto**: Build > Make Project
* **Testar o aplicativo**: Executar no emulador ou dispositivo Android

## Tecnologias e Estilo de Código

* **Linguagem**: Kotlin.
* **Interface**: Jetpack Compose.
* **Banco de dados**: Room.
* **Navegação**: Navigation Compose.
* **WhatsApp**: Usar `Intent` para abrir o WhatsApp com a mensagem da proposta.
* **Código**: Manter simples, organizado e fácil de entender pela equipe.
* **Strings**: Textos visíveis do aplicativo devem ficar em `strings.xml`.

## Entidades do Banco

* **Cliente**: `id`, `nome`, `telefone` e `email`.
* **Serviço**: `id`, `nome`, `descricao` e `preco`.
* **Pedido**: `id`, `orcamento`, `data` e `status`.
* **Login**: `id`, `email`, `usuario` e `senha`.

## Regras do Projeto

* Consultar o `PRD.md` antes de fazer alterações importantes.
* Não remover funcionalidades que já estão funcionando.
* Fazer apenas as alterações necessárias.
* Evitar código duplicado.
* Usar Room para salvar e consultar os dados.
* Os dados devem continuar salvos mesmo depois de fechar e abrir o aplicativo.

## Login

* O usuário deve informar **usuário e senha**.
* O login deve ser verificado antes de acessar as telas principais.
* A senha não deve ficar visível durante a digitação.
* Se os dados estiverem incorretos, mostrar uma mensagem de erro sem fechar o aplicativo.

## WhatsApp

* Usar `Intent` para abrir o WhatsApp.
* A mensagem da proposta deve ser preenchida automaticamente.
* Se o WhatsApp não estiver disponível, mostrar uma mensagem de erro sem fechar o aplicativo.

## Limites e Cuidados

* Não adicionar funcionalidades que estejam fora do escopo do projeto.
* Não incluir pagamentos, chat próprio, entrega/rastreamento ou consulta de CEP.
* Não apagar ou alterar código existente sem necessidade.
* Todo código gerado por IA deve ser revisado pela equipe.
* Todos os integrantes devem entender e conseguir explicar o código utilizado.
