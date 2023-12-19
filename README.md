# Clinica Psicologia Infantil

Aplicação desktop desenvolvida em JavaFX utilizando os frameworks Spring para injeção de dependências e manipulação do banco de dados com JPA. Scene Builder para construção das telas e componentes visuais, Lombok para eliminar a necessidade de criação dos getters e seters e dos construtores das classes e Banco de Dados relacional Postgres para armazenamento das informações do sistema. O objetivo da aplicação é prover o gerenciamento de uma clínica de psicologia especializada no público infantil através da manutenção dos cadastros de médicos e pacientes, além da marcação e realização de consultas, deixando registrado todo o acompanhamento das devolutivas dos médicos no banco de dados. Também é possível gerenciar a prescrição de receitas para os pacientes.

# Java e JavaFX

Essa aplicação foi desenvolvida utilizando a versão Java 17 e a biblioteca gráfica JavaFX na versão 21.

# Spring FrameWork

Essa aplicação foi desenvolvida utilizando a versão 3.2.0 do Spring Framework

# Arquitetura MVC com DTO

Essa aplicação implementa a arquitetura de projeto no padrão MVC (Model, View e Controller) visando uma melhor separação das responsabilidades das classes por camadas. Utilizamos o padrão DTO (Data Transfer Object) para trafegas as informações entre camadas evitando assim de trafegar as entidades.

# Banco de Dados

Essa aplicação usa em produção o banco de dados relacional Postgresql versão 42.7.1 que pode ser gerenciado através do SGBD pgAdmin. Para fins de teste ela utiliza o banco de dados em memória M2 que conta com um script data.sql para popular alguns dados inciais no momento que a aplicação e executada.

# Modelo Entidade Relacional

Foi criado um modelo Entidade Relacional (ER) para demonstração das estruturas e relacionamentos das tabelas utilizadas pela aplicação utilizando a ferramenta PowerDesigner. Os arquivo gerados por essa ferramenta encontram-se na pasta mencionada abaixo assim como uma versão em imagem:

- \ClinicaPsicologiaInfantil\modelorelacional

# Referências

Para fazer funcionar o Spring Framework juntamente com o JavaFX segui o tutorial mostrado no site da JetBrains que é o fabricante da IDE IntelliJ conforme link abaixo:

IntelliJ IDEA by JetBrains
https://www.youtube.com/watch?v=u0dEf-QN-90&t=50s

JavaFX Spring Boot Application
https://www.youtube.com/watch?v=01GTN2iXbd8
