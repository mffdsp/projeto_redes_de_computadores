<br />
<p align="center">
  <a href="https://github.com/mffdsp/projeto_redes_de_computadores">
    <img src="images/redes.png" alt="Logo" width="500" height="300">
  </a>

  <h3 align="center">Projeto Redes de Computadores - Implementação Sockets</h3>




<details open="open">
  <summary>CONTEÚDO</summary>
  <ol>
    <li>
      <a href="#sobre-o-projeto">Sobre o projeto</a>
    </li>
    <li>
      <a href="#iniciando">Iniciando</a>
      <ul>
        <li><a href="#pré-requisitos">Pré-requisitos</a></li>
      </ul>
    </li>
    <li><a href="#execução">Execução</a></li>
  </ol>
</details>




## Sobre o projeto



Este projeto se trata de uma implementação do conceito de sockets utilizando a linguagem de programação Java. É baseado em um simples sitema de delivery de pizza.
O cliente faz a requisição, uma de cada vez, de um sabor de pizza e seu tamanho (pequeno, médio, grande).
No final do pedido, o servidor envia um arquivo .txt que representa uma nota fiscal.


<!-- GETTING STARTED -->
## Iniciando
Veja o que precisa para rodar o projeto em seu computador.

### Pré-requisitos
Você pode executar diretamente no terminal ou utilizando uma IDE

- Compilador java
  - [Windows](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
  - [Mac](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
  - Linux:
     ```sh
     sudo apt install default-jdk
     ```
 
 - IDE
   - [IntelliJ](https://www.jetbrains.com/pt-br/idea/download/)
   - [Eclipse](https://www.eclipse.org/downloads/packages/)
 





## Execução

- Terminal:
  - Servidor
     ```sh
     java DeliveryServer [número da porta]
     ```
  - Cliente
     ```sh
     java DeliveryClient [host/IP] [número da porta]
     ```
   - Exemplo:
     - Servidor
     ```sh
     java DeliveryServer 4444
     ```
      - Cliente
     ```sh
     java DeliveryClient localhost 4444
     ```
- IDE
  - Execute o DeliveryServer com o parâmetro do número da porta. Ex: 4444
  - Execute o DeliveryClient com o parâmetro do host e número da porta. Ex: localhost 4444 


