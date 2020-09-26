# Teste Prático Pública (TPP)

Projeto desenvolvido para o proceso de seleção organizado pela Proway para a empresa Pública em 2020. 

## Requisitos

- MySQL 5.7
- JDK 1.8+
- Lombok 
	- https://projectlombok.org/download

## Instalação do Lombok em sua IDE
- https://projectlombok.org/setup/eclipse
	- Após baixar a biblioteca Lombok
	- Certifique-se de está com a IDE fechada
	- Dê um duplo clique no arquivo lombok.jar
	- Clique em Specify location
	- Encontre o caminho do executavel da sua IDE e o selecione
	- Clique em intall/Update
	- Pronto pode fechar a execução do Lombok e abrir sua IDE

## Para executar
- Clone o projeto para sua máquina
- Importe o projeto para sua IDE utilizando a opção projeto maven existente	
- Inicie a execução do mysql
- Altere as propriedades do arquivo application.properties no diretório src/main/resources e application-test.properties no diretório src/test/resources
de acordo com as configurações do seu ambiente de desenvolvimento: 

	- spring.datasource.username=Username do seu banco de dados 
	- spring.datasource.passord=Password do seu banco de dados

- Executando o projeto
	- Execute a classe TestePraticoPublicaApplication com Run As Application Java
	- Acesse a URL http://localhost:8080 no navegador