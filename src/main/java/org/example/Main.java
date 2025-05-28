package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static String url = "jdbc:postgresql://localhost:5432/escola-teach";
    public static String user = "postgres";
    public static String password = "root";

    public static void main(String[] args) {
        verificadorIncial();
        menuPrincipal();
    }

    //se não houver alguma tabela, ele usa o "criacaoTabelas()" para criar elas
    public static void verificadorIncial() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("desafio-1.5");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        boolean exists = verificaDeTabelas(entityManager);

        if (!exists) {
            criacaoTabelas();
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    //o select é usando para verificar, procurando pela PU "desafio-1.5", se no database "escola-teach" há alguma tabela
    public static boolean verificaDeTabelas(EntityManager em) {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public'";
        Number count = (Number) em.createNativeQuery(sql).getSingleResult();
        return count.intValue() > 0;
    }

    public static void criacaoTabelas() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("desafio-1.5");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void menuPrincipal() {
        System.out.println("\nBem vindo ao Sistema de Matrículas");
        System.out.println("\nAntes de qualquer coisa, certifique-se de criar o Database no PgAdmin 4 com o Nome de 'escola-teach'");
        System.out.println("Sem ele, não será possível começar a trabalhar.");

        boolean isUsando = true;
        while (isUsando) {
            try {
                System.out.println("\nSistema de Matrícula");
                System.out.println("1. Inserir Aluno ou Curso");
                System.out.println("2. Matricular");
                System.out.println("3. Listar Alunos e Cursos");
                System.out.println("4. Listar Matrículas");
                System.out.println("5. Sistema de Buscas");
                System.out.println("6. Relatório de Engajamento");
                System.out.println("0. Sair");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> menuDeInsercao();
                    case 2 -> menuMatricular();
                    case 3 -> {
                        listarAlunos();
                        listarCursos();
                    }
                    case 4 -> listarMatriculas();
                    case 5 -> menuSistemaBuscas();
                    case 6 -> relatorioDeEngajamento();
                    case 0 -> {
                        System.out.println("\nObrigado por Utilizar o Sistema.");
                        isUsando = false;
                    }
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
            //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }
    }

    public static void menuDeInsercao() {
        boolean isUsando = true;
        while (isUsando) {
        try {
                System.out.println("\nSistema de Inserção");
                System.out.println("1. Inserir Aluno");
                System.out.println("2. Inserir Curso");
                System.out.println("0. Voltar");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> menuDeInsercaoAlunos();
                    case 2 -> menuDeInsercaoCursos();
                    case 0 -> isUsando = false;
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
            //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }
    }

    public static void menuDeInsercaoAlunos() {
        scanner.nextLine();
        String nome = "";
        String email = "";
        String dataNascimento = "";

        //verifica se o campo não está em branco e como o nome pode conter até número e caracter, ele não verifica isso
        System.out.println("\nCadastro dos ALUNOS");
        while (nome.isBlank()) {
            System.out.print("Digite o NOME do Aluno: ");
            nome = scanner.nextLine();
        }

        //verifica se o email contém "@" e ".com", como existem diversos provedores de email, considerei não colocar todos eles para não extender o código
        while (email.isBlank() || !email.contains("@") || !email.contains(".com")) {
            System.out.print("Digite o EMAIL do Aluno: ");
            email = scanner.nextLine();
        }

        //para que não desse nenhum erro, fiz o usuário digitar os dados que complementam a data de nascimento de forma separada e verifiquei eles individualmente
        boolean isEscrevendo = true;
        while (isEscrevendo) {
            try {
                System.out.println("\nData de Nascimento");

                //o dia não pode conter mais que 31, considerei, pelo tempo, não colocar exatamente como são nos meses
                int dia = 0;
                while (dia > 31 || dia < 1) {
                    System.out.print("Digite o DIA (01): ");
                    dia = scanner.nextInt();
                }

                //um ano só tem 12 meses
                int mes = 0;
                while (mes > 12 || mes <1) {
                    System.out.print("Digite o MES (01): ");
                    mes = scanner.nextInt();
                }

                //coloquei a data mínima para se cadastrar como 1900 e a data máxima, o ano atual
                int ano = 0;
                LocalDate thisYear = LocalDate.now();
                while (ano > thisYear.getYear() || ano < 1900) {
                    System.out.print("Digite o ANO (2001): ");
                    ano = scanner.nextInt();
                }

                //junção de todos os dados da forma como deve ser inserida no banco de dados
                dataNascimento = ano + "-" + mes + "-" + dia;
                isEscrevendo = false;

            //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        //chama a função de inserção dos dados de fato, mandando o valores que peguei do usuário
        inserirAlunos(nome, email, dataNascimento);
    }

    public static void inserirAlunos(String nome, String email, String dataNascimento) {
        String sql = "INSERT INTO aluno (nome, email, dataNascimento) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setDate(3, java.sql.Date.valueOf(dataNascimento));

            statement.executeUpdate();

            //como a verificação está estruturada para os dados chegarem aqui como devem entrar no banco de dados e por conta do tempo, não coloquei uma verificação para ver se entraram no banco
            System.out.println("\nInserção Concluída com Sucesso.");

        //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void menuDeInsercaoCursos(){
        scanner.nextLine();
        String nome = "";
        String descricao = "";
        int cargaHoraria = 0;

        //verifica se o campo não está em branco
        System.out.println("\nCadastro dos CURSOS");
        while (nome.isBlank()) {
            System.out.print("Digite o NOME do Curso: ");
            nome = scanner.nextLine();
        }

        //verifica se o campo não está em branco
        while (descricao.isBlank()) {
            System.out.print("Digite o DESCRIÇÃO do Curso: ");
            descricao = scanner.nextLine();
        }

        //verifica se não há algo além de números
        boolean isEscrevendo = true;
        while (isEscrevendo) {
            try {
                System.out.print("Digite a CARGA HORÁRIA do Curso (horas): ");
                cargaHoraria = scanner.nextInt();
                isEscrevendo = false;
            //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        //chama a função de inserção dos dados de fato, mandando o valores que peguei do usuário
        inserirCurso(nome, descricao, cargaHoraria);
    }

    public static void inserirCurso(String nome, String descricao, Integer cargaHoraria){
        String sql = "INSERT INTO curso (cargaHoraria, descricao, name) VALUES (?, ?, ?)";
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            //preenche as variáveis em branco do "sql"
            statement.setInt(1, cargaHoraria);
            statement.setString(2, descricao);
            statement.setString(3, nome);

            //realiza a ação
            statement.executeUpdate();

            System.out.println("\nInserção Concluída com Sucesso.");

        //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void menuMatricular() {
        scanner.nextLine();
        String dataMatricula = "";
        int curso_id = 0;
        int aluno_id = 0;

        System.out.println("\nCadastro dos MATRÍCULAS");
        boolean isEscrevendoDataMatr = true;
        while (isEscrevendoDataMatr) {
            try {
                //como é uma data, fiz o mesmo que a data de nascimento, verificando cada dado individualmente
                System.out.println("\nData de Matrícula");

                //o dia que não pode ter mais de 31
                int dia = 0;
                while (dia > 31 || dia < 1) {
                    System.out.print("Digite o DIA (01): ");
                    dia = scanner.nextInt();
                }

                //o mes que não pode ter mais de 12
                int mes = 0;
                while (mes > 12 || mes <1) {
                    System.out.print("Digite o MES (01): ");
                    mes = scanner.nextInt();
                }

                //o ano que deve estar depois de 1900 e antes do ano atual
                int ano = 0;
                LocalDate thisYear = LocalDate.now();
                while (ano > thisYear.getYear() || ano < 1900) {
                    System.out.print("Digite o ANO (2001): ");
                    ano = scanner.nextInt();
                }

                //junção de todos os dados da forma como deve ser inserida no banco de dados
                dataMatricula = ano + "-" + mes + "-" + dia;
                isEscrevendoDataMatr = false;

                //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        listarCursos();
        boolean isEscrevendoCurso_Id = true;
        while (isEscrevendoCurso_Id) {
            try {
                System.out.print("Digite o Identificador do CURSO: ");
                curso_id = scanner.nextInt();

                String verificadorCurso = "SELECT COUNT(*) FROM curso WHERE id = ?";
                try {
                    //conexão com o banco e prepara o statement que será enviado
                    Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement statement = connection.prepareStatement(verificadorCurso);

                    //preenche as variáveis em branco do "sql"
                    statement.setInt(1, curso_id);
                    //realiza a ação
                    ResultSet resultSet = statement.executeQuery();

                    //
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        isEscrevendoCurso_Id = false;
                    } else {
                        System.out.println("Identificador Não Encontrado. Tente novamente.");
                    }
                    //catch personalizado para os erros do banco durante o tempo de execução do código
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        listarAlunos();
        boolean isEscrevendoAluno_Id = true;
        while (isEscrevendoAluno_Id) {
            try {
                System.out.print("Digite o Identificador do ALUNO: ");
                aluno_id = scanner.nextInt();

                String verificadorAluno = "SELECT COUNT(*) FROM aluno WHERE id = ?";
                try {
                    //conexão com o banco e prepara o statement que será enviado
                    Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement statement = connection.prepareStatement(verificadorAluno);

                    //preenche as variáveis em branco do "sql"
                    statement.setInt(1, aluno_id);
                    //realiza a ação
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        isEscrevendoAluno_Id = false;
                    } else {
                        System.out.println("Identificador Não Encontrado. Tente novamente.");
                    }
                    //catch personalizado para os erros do banco durante o tempo de execução do código
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        //chama a função de matricula, mandando o valores que peguei do usuário
        matricular(dataMatricula, curso_id, aluno_id);
    }

    public static void matricular(String dataMatricula, int curso_id, int aluno_id) {
        String sql = "INSERT INTO matricula (dataMatricula, curso_id, aluno_id) VALUES (?, ?, ?)";
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            //preenche as variáveis em branco do "sql"
            statement.setDate(1, java.sql.Date.valueOf(dataMatricula));
            statement.setInt(2, curso_id);
            statement.setInt(3, aluno_id);

            //realiza a ação
            statement.executeUpdate();

            System.out.println("\nMatrícula Concluída com Sucesso.");

            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarAlunos() {
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            //código que será enviado para o banco
            String sql = "SELECT * FROM aluno";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Alunos\n");
            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String dataNascimento = String.valueOf(resultSet.getDate("dataNascimento"));
                System.out.println("ID: " + id + " / Nome: " + nome + " / Email: " + email + " / Data de Nascimento: " + dataNascimento);
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarCursos() {
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM curso";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Cursos\n");
            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("name");
                int cargaHoraria = resultSet.getInt("cargaHoraria");
                String descricao = resultSet.getString("descricao");
                System.out.println("ID: " + id + " / Nome: " + nome + " / Descrição: " + descricao + " / Carga Horária: " + cargaHoraria);
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarMatriculas(){
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "SELECT matricula.id, matricula.curso_id, matricula.aluno_id, matricula.dataMatricula, curso.name, aluno.nome " +
                    "FROM matricula " +
                    "LEFT JOIN curso ON matricula.curso_id = curso.id " +
                    "LEFT JOIN aluno ON matricula.aluno_id = aluno.id";

            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Matriculas\n");
            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dataMatricula = String.valueOf(resultSet.getDate("dataMatricula"));
                String cursoName = resultSet.getString("name");
                String alunoNome = resultSet.getString("nome");
                System.out.println("Número da Matrícula: " + id + " / Data da Matrícula: " + dataMatricula + " / Nome do Curso: " + cursoName + " / Nome do Aluno: " + alunoNome);
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void menuSistemaBuscas(){
        boolean isUsando = true;
        while (isUsando) {
            try {
                System.out.println("\nSistema de Buscas");
                System.out.println("1. Buscar Aluno");
                System.out.println("2. Buscar Curso");
                System.out.println("0. Voltar");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1, 2 -> sistemaBuscas(option);
                    case 0 -> isUsando = false;
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
                //o catch para pegar tudo que não seja número e usa o while para continuar o código até ele digitar uma opção válida
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }
    }

    public static void sistemaBuscas(int option){
        scanner.nextLine();
        try {
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement;

            //a partir da escolha do usuário, ele manda um select pro banco usando o like para passar o parâmetro de busca
            String parametroBusca;
            if (option == 1) {
                String sql = "SELECT * FROM aluno where email LIKE ?";
                statement = connection.prepareStatement(sql);

                System.out.print("\nDigite o EMAIL do Aluno (ou parte dele): ");
                parametroBusca = scanner.nextLine();

                statement.setString(1, parametroBusca + "%");
            } else {
                String sql = "SELECT * FROM curso where name LIKE ?";
                statement = connection.prepareStatement(sql);

                System.out.print("\nDigite o NOME do Curso (ou parte dele): ");
                parametroBusca = scanner.nextLine();

                statement.setString(1, parametroBusca + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            System.out.print("\nLista de Resultados\n");
            String nome;
            String descricao = "";
            String email = "";
            String dataNascimento = "";
            int cargaHoraria = 0;

            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (option == 1) {
                    nome = resultSet.getString("nome");
                    email = resultSet.getString("email");
                    dataNascimento = String.valueOf(resultSet.getDate("dataNascimento"));
                } else {
                    nome = resultSet.getString("name");
                    descricao = resultSet.getString("descricao");
                    cargaHoraria = resultSet.getInt("cargaHoraria");
                }

                if (option == 1) {
                    System.out.println("ID: " + id + " / Nome: " + nome + " / Email: " + email + " / Data de Nascimento: " + dataNascimento);
                } else {
                    System.out.println("ID: " + id + " / Nome: " + nome + " / Descrição: " + descricao + " / Carga Horária: " + cargaHoraria);
                }
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //o relatorio foi dividido em 3 querys diferentes para o melhor entendimento
    public static void relatorioDeEngajamento() {
        System.out.println("\nRelatório de Enganjamento (POR CURSO)");

        LocalDate hoje = null;
        int numero;
        try {
            System.out.println("Total de alunos matriculados: ");
            //código que será enviado pelo statament
            String sql = "SELECT COUNT(*), curso.name FROM matricula " +
                    "LEFT JOIN curso ON matricula.curso_id = curso.id " +
                    "GROUP BY curso.name";
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                numero = resultSet.getInt(1);
                String curso = resultSet.getString("name");

                System.out.println("Curso: " + curso + " / Numero de Matrículas: " + numero);
            }


            System.out.println("\nMédia de idade dos alunos no curso: ");
            sql = "SELECT aluno.dataNascimento, curso.name FROM matricula " +
                    "LEFT JOIN curso ON matricula.curso_id = curso.id " +
                    "LEFT JOIN aluno ON matricula.aluno_id = aluno.id ";
            //conexão com o banco e prepara o statement que será enviado
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            Map<String, List<Integer>> cursos = new HashMap<>();

            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                LocalDate dataNascimento = resultSet.getDate("dataNascimento").toLocalDate();
                String curso = resultSet.getString("name");
                hoje = LocalDate.now();
                //"traduzo" a data em anos, usando o periodo do de hoje para a data de nascimento
                int idade = Period.between(dataNascimento, hoje).getYears();

                //criação das arraylist de acordo com o curso, se já existir o curso, o valor da idade é somente adicionado ao arraylist, se não, ele cria um arraylist novo
                cursos.computeIfAbsent(curso, k -> new ArrayList<>()).add(idade);
            }
            for (String curso : cursos.keySet()) {
                List<Integer> idades = cursos.get(curso);
                //calcula a média dos valores da lista idades, cria um stream da lista, converte os Integer em int e calcula a média
                double media = idades.stream().mapToInt(i -> i).average().orElse(0);
                System.out.println("Curso: " + curso + " / Média de idade: " + Math.round(media) + " anos");
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("\nQuantidade de alunos matriculados nos últimos 30 dias: ");

            assert hoje != null;
            //data de hoje menos 30 dias
            LocalDate hojeMinus30 = hoje.minusDays(30);
            //conexão com o banco e prepara o statement que será enviado
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT COUNT(*), curso.name FROM matricula " +
                    "LEFT JOIN curso ON matricula.curso_id = curso.id " +
                    "WHERE matricula.dataMatricula > ? " +
                    "GROUP BY curso.name";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(hojeMinus30));

            ResultSet resultSet = statement.executeQuery();

            //retorno do banco, que mostra até que os itens terminem
            while (resultSet.next()) {
                String curso = resultSet.getString("name");
                int matriculas = resultSet.getInt(1);

                System.out.println("Curso: " + curso + " / Matrículas: " + matriculas);
            }
            //catch personalizado para os erros do banco durante o tempo de execução do código
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}