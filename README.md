# Projeto de Gerenciamento de Dados

Este projeto é uma aplicação que gerencia dados sensíveis, onde implementamos várias funcionalidades como criptografia de dados, persistência de informações usando o Room, e organização com a arquitetura MVI (Model-View-Intent). A seguir, são descritas as tecnologias e funcionalidades implementadas:

## Tecnologias Utilizadas

- **Compose**: Utilizado para a criação da interface de usuário de forma declarativa.
- **Gradle**: Utilizado para gerenciamento de dependências e configuração do build do projeto.
- **Room**: Persistência de dados, utilizado para salvar e gerenciar logs.
- **Coroutines**: Usadas para execução assíncrona, garantindo performance no carregamento de dados.
- **Cryptografia**: Implementação de criptografia de dados utilizando `Cipher` e `EncryptedSharedPreferences` com codificação em Base64.
- **MVI Architecture**: Arquitetura Model-View-Intent foi utilizada para organizar o fluxo de dados entre a UI e a camada de negócios de forma clara e escalável.
- **Testes Unitários**: Implementação de testes unitários para garantir a integridade das funcionalidades.

## Funcionalidades

O aplicativo possui três telas principais:

1. **Tela de Login**:
   - A tela de login permite que o usuário faça a autenticação no sistema.

2. **Tela de Apresentação de Dados**:
   - Exibe dados criptografados e descriptografados ao mesmo tempo.
   - A criptografia de dados é realizada utilizando o `Cipher` e `EncryptedSharedPreferences` com Base64 para garantir segurança.

3. **Tela de Logs**:
   - Exibe logs de ações realizadas no aplicativo.
   - Logs são persistidos no banco de dados utilizando o Room para garantir a persistência e integridade dos dados.

## Arquitetura

A arquitetura do projeto segue o padrão **MVI (Model-View-Intent)**, proporcionando um fluxo claro e eficiente de dados e ações dentro do aplicativo. A implementação de coroutines também garante que as tarefas assíncronas sejam tratadas de forma adequada.

## Criptografia de Dados

Para garantir a segurança dos dados sensíveis, implementamos criptografia de dados no aplicativo utilizando:

- **Cipher**: Para criptografar e descriptografar informações.
- **EncryptedSharedPreferences**: Para armazenar dados sensíveis de maneira segura.
- **Base64**: Para codificar os dados criptografados antes de serem armazenados.

## Persistência de Dados

Os logs gerados pelo aplicativo são persistidos utilizando a biblioteca **Room**. Isso garante que os dados importantes, como o histórico de ações do usuário, sejam armazenados de maneira eficiente e recuperados posteriormente quando necessário.

## Coroutines

A utilização de **coroutines** permite o processamento assíncrono, melhorando a performance do aplicativo e evitando bloqueios na interface do usuário durante operações de leitura/escrita de dados.

## Testes Unitários

A aplicação foi desenvolvida com a prática de **testes unitários** para garantir que cada unidade do código funcione corretamente. A estrutura de testes ajuda a validar o comportamento da criptografia, persistência de logs, e outras funcionalidades importantes.

## Como Rodar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/Letheann/desafio-android.git


## Como Mudar para o Branch 'mercantil'

Para mudar para o branch 'mercantil', siga os passos abaixo:

1. Abra o terminal no diretório do projeto.
2. Execute o comando:
   ```bash
   git checkout mercantil


