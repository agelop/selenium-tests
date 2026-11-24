# selenium-tests

Projeto de testes automatizados de interface com Selenium WebDriver e JUnit 4. Os testes exercitam o site Advantage Online Shopping em um navegador Chrome e demonstram diferentes formas de organizar a automacao e localizar elementos.

## Visao geral

- Linguagem: Java 17
- Gerenciador de build: Apache Maven
- Framework de testes: JUnit 4.13.2
- Automacao de navegador: Selenium `4.54.0`
- Gerenciamento do ChromeDriver: WebDriverManager `6.4.0`
- Pipeline: Azure Pipelines executando o objetivo `package` com JDK 17

O Maven tambem utiliza o Maven Compiler Plugin `3.14.1` e o Maven Surefire Plugin `3.6.1`.

## Dependencias

- **Selenium Java**: fornece `WebDriver`, interacoes com elementos, condicoes esperadas e esperas sincronizadas.
- **JUnit 4**: disponibiliza as anotacoes de ciclo de vida e assertivas dos testes.
- **WebDriverManager**: baixa e configura automaticamente a versao do ChromeDriver compativel com o Chrome instalado.
- **Maven Compiler Plugin**: compila o projeto para Java 17.
- **Maven Surefire Plugin**: executa os testes JUnit e gera os relatórios usados pelo Azure Pipelines.

## Executando o projeto

Instale Java 17, Maven e Chrome. Depois, execute:

```bash
mvn test
```

Para compilar e empacotar o projeto sem executar os testes:

```bash
mvn -DskipTests package
```

Os testes dependem da disponibilidade do site Advantage Online Shopping e dos dados da conta:

- Usuario: `Mercury`
- Senha: `Mercury`

No ambiente de CI, o pipeline publica os arquivos `**/surefire-reports/TEST-*.xml`.

## Estrutura principal

- `src/test/java/selenium/tests/RegressionTest.java`: testes independentes de regressao de produtos.
- `src/test/java/selenium/tests/LoginTest.java`: cinco variantes do mesmo fluxo de login/logout, demonstrando abordagens diferentes de automacao.
- `src/main/java/tests/BaseTest.java`: fornece a classe compartilhada que inicia o navegador uma vez e o encerra no final da suite.
- `src/main/java/page/objects`: Page Objects que representam a pagina principal e encapsulam elementos e acoes.
- `src/main/java/flows`: fluxos de negocio que combinam varias acoes das Page Objects.

## LoginTest

Todos os metodos desta classe utilizam a classe `BaseTest`, que abre e fecha o Chrome uma vez para a suite.

### `checkLoggedUser`

Demonstracao direta em Selenium:

1. Abre o site Advantage Online Shopping.
2. Aguarda o elemento `.loader` ficar oculto.
3. Localiza e clica no icone de usuario.
4. Preenche usuario e senha.
5. Clica em Entrar.
6. Aguarda a atualizacao da interface.
7. Lê o nome do usuario autenticado e valida que corresponde a `Mercury`.
8. Abre o menu do usuario e realiza logout.

### `checkLoggedUser1`

Executa o mesmo fluxo por meio da `HomePage`, declarada com anotacoes `@FindBy`.

### `checkLoggedUser2`

Executa o fluxo por meio da `MainPage`, que usa locators `By` explicitamente.

### `checkLoggedUser3`

Delega a entrada e saida da conta ao `MainFlows`, construido sobre a `MainPage`.

### `checkLoggedUser4`

Delega a entrada e saida da conta ao `HomeFlows`, construido sobre a `HomePage`.

Os metodos `checkLoggedUser1` a `checkLoggedUser4` mostram progressivamente mais encapsulamento: Page Object com anotacoes, Page Object com locators, fluxo baseado em `MainPage` e fluxo baseado em `HomePage`.

## RegressionTest

Esta classe administra seu proprio ciclo de vida com JUnit:

- `@BeforeClass`: configura o ChromeDriver e cria o `WebDriver` e o `WebDriverWait`.
- `@Before`: abre o site, aguarda o carregamento, realiza login e aguarda a interface ser atualizada.
- `@After`: abre o menu do usuario e realiza logout.
- `@AfterClass`: fecha o navegador.

### `checkTabletPrice`

1. Clica na categoria Tablets.
2. Seleciona o produto “HP Pro Tablet 608 G1”.
3. Localiza o preco na pagina de detalhes.
4. Remove espacos da leitura e valida que o valor é `$479.00`.

### `checkSpeakerPrice`

1. Clica na categoria Speakers.
2. Seleciona o produto “HP S9500 Bluetooth Wireless Speaker”.
3. Localiza o preco na pagina de detalhes.
4. Remove espacos da leitura e valida que o valor é `$201.00`.

## Page Objects e fluxo de negocio

Uma Page Object representa uma area da interface e concentra elementos e operacoes dessa area. Isso evita repetir locators e comandos Selenium nos testes.

`BasePage` possui o `WebDriver`, uma espera explicita de 30 segundos com intervalo de pesquisa de cinco segundos e inicializa os elementos com `AjaxElementLocatorFactory`. Dessa forma, elementos dinamicos obtidos pela PageFactory podem aguardar o tempo configurado.

Existem duas implementacoes da pagina de login:

- `HomePage`: demonstra a PageFactory com elementos anotados por `@FindBy`.
- `MainPage`: demonstra locators `By` armazenados em atributos da classe.

Ambas oferecem operacoes equivalentes, como clicar no usuario, preencher usuario e senha, entrar, ler o usuario autenticado e sair.

`MainFlows` e `HomeFlows` trabalham em um nivel mais alto: adicionam um fluxo de negocio, combinando os metodos da Page Object em `SignIn` e `SignOut`. Os testes ficam mais legiveis, mas continuam separando o comportamento da loja dos detalhes dos locators.

## Abordagens de identificacao de elementos

O projeto demonstra varias alternativas de Selenium:

| Estrategia | Exemplo no projeto | Observacao |
| --- | --- | --- |
| ID | `By.id("hrefUserIcon")` | Preferivel quando o identificador é estavel e unico. |
| Name | `By.name("username")` | Util para campos HTML com atributo `name` bem definido. |
| Class name | `By.className("loader")` | Adequado para elementos identificados por uma classe previsivel. |
| XPath | `By.xpath("//*[@id=\"menuUserLink\"]/span")` | Permite navegar pela estrutura da pagina e pode ser necessario em layouts complexos. |
| Texto do link | `By.linkText("HP Pro Tablet 608 G1")` | Localiza pelo texto visivel de links e é legivel. |
| PageFactory com `@FindBy` | `@FindBy(id = "hrefUserIcon")` | Separa a declaracao do elemento da logica da Page Object. |
| Locator em atributo `By` | `By userIconLocator = By.id(...)` | Facilita reutilizar e centralizar locators em uma classe. |

Como boas praticas, IDs sao geralmente preferiveis a XPaths extensos. Locators associados a estrutura, classes visuais ou textos podem deixar de funcionar quando o site muda. O projeto centraliza muitos deles nas Page Objects para reduzir o custo de manutencao.

## Sincronizacao

Em vez de apenas usar esperas fixas, o projeto combina `WebDriverWait` com condicoes esperadas:

- `visibilityOfElementLocated`: aguarda um elemento ficar visivel.
- `elementToBeClickable`: aguarda um elemento estar visivel e habilitado para clique.
- `attributeToBe`: aguarda um atributo atingir o valor esperado.
- `visibilityOf`: aguarda o elemento previamente criado pela PageFactory ficar visivel.

Os exemplos usam timeout de 30 segundos e pesquisa do estado a cada cinco segundos. Isso permite que o teste aguarde o carregamento assíncrono da pagina sem repetir chamadas diretas ao DOM.
