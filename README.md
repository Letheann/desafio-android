# Desafio Android

Este projeto é uma aplicação Android desenvolvida com foco em testes de UI e integração, utilizando **Jetpack Compose** para a interface de usuário e **MockK** para testes unitários e de UI. A arquitetura utilizada segue o padrão **MVI (Model-View-Intent)** para uma estrutura clara e escalável. A seguir, são descritas as tecnologias e funcionalidades implementadas.

## Tecnologias Utilizadas

- **Jetpack Compose**: Framework moderno para criação de interfaces de usuário de forma declarativa e reativa.
- **Gradle**: Gerenciamento de dependências e configuração do build do projeto.
- **Coroutines**: Execução assíncrona de tarefas, garantindo a performance no carregamento de dados e interações com a UI.
- **MockK**: Framework utilizado para mockar objetos e realizar testes unitários e de integração.
- **JUnit**: Framework de testes para garantir a qualidade e integridade das funcionalidades do projeto.
- **MVI Architecture**: Arquitetura Model-View-Intent, que organiza o fluxo de dados e ações de maneira eficiente e fácil de testar.

## Funcionalidades

O aplicativo possui as seguintes funcionalidades principais:

1. **Tela de Listagem de Itens (RecyclerView)**:
   - Exibe uma lista de itens que podem ser clicados para exibir detalhes adicionais.
   - Utiliza o **Jetpack Compose** para renderizar a interface de forma eficiente.

2. **Navegação entre Telas**:
   - A navegação é realizada ao clicar nos itens da lista, acionando um efeito que dispara a navegação para uma tela de detalhes.
   - O fluxo de navegação é controlado através da arquitetura **MVI**, com **ViewEffect** para gerenciar os efeitos.

## Arquitetura

O projeto segue a arquitetura **MVI (Model-View-Intent)**, onde:

- **Model**: A lógica de negócios e os dados são gerenciados por `ViewModel`s e `UseCases`.
- **View**: A interface de usuário é construída com **Jetpack Compose** e controlada pelo `ViewModel`.
- **Intent**: As ações do usuário são mapeadas como **Intents**, que são enviadas ao `ViewModel` para atualizar o estado da UI.
- **ViewEffect**: Efeitos de navegação ou interações que podem ser desencadeados como resposta aos **Intents**.

### MVI Workflow

1. O **View** (UI) envia **Intents** para o **ViewModel**.
2. O **ViewModel** manipula esses **Intents**, atualiza o **State** e pode gerar **ViewEffects**.
3. O **View** reage a esses **State** e **ViewEffects**, atualizando a interface e executando ações como navegação ou animações.

## Testes

**Testes Unitários**:
   - Utilizamos **MockK** para mockar as dependências e garantir que os métodos do **ViewModel** e **UseCases** sejam chamados corretamente.
   - O uso de **JUnit** permite que os testes sejam executados de forma simples e eficiente.

## Configuração da API

Para utilizar os serviços da **CoinAPI.io**, é necessário configurar as chaves de API no arquivo `local.properties`. Adicione as seguintes linhas ao arquivo:

```
key.public = 1f8bac3e-021e-4315-8b3e-d9d1e657c39f
key.private = 1f8bac3e-021e-4315-8b3e-d9d1e657c39f
```

Isso garantirá que o aplicativo tenha acesso à API e possa obter os dados necessários para seu funcionamento.

## Como Rodar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/desafio-android.git
   ```

2. Abra o projeto no Android Studio.

3. Compile e execute o projeto no emulador ou dispositivo físico.

4. Para rodar os testes, execute o comando abaixo:
   ```bash
   ./gradlew test
   ```

## Como Mudar para o Branch 'MercadoBitcoin'

Para mudar para o branch 'MercadoBitcoin', siga os passos abaixo:

1. Abra o terminal no diretório do projeto.
2. Execute o comando:
   ```bash
   git checkout MercadoBitcoin
   ```

## Licença

Este projeto é licenciado sob a [Licença MIT](LICENSE).

