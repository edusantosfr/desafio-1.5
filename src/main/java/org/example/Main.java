package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static String url = "jdbc:postgresql://localhost:5432/escola-teach";
    public static String user = "postgres";
    public static String password = "host";

    public static void main(String[] args) {

        boolean createTables = false;
        if (createTables) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("desafio-1.5");

            EntityManager entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            entityManager.close();
            entityManagerFactory.close();
        }

        menuPrincipal();
    }

    public static void menuPrincipal() {
        System.out.println("Bem vindo ao Sistema de Matrículas");
        System.out.println("\nAntes de qualquer coisa, certifique-se de criar o Database no PgAdmin 4");
        System.out.println("Sem ele, não será possível começar a trabalhar.");

        System.out.println("\n//");

        boolean isUsando = true;
        while (isUsando) {
            try {
                System.out.println("\nSistema de Matrícula");
                System.out.println("1. Inserir Aluno ou Curso");
                System.out.println("2. Matricular");
                System.out.println("3. Listar Alunos e Cursos");
                System.out.println("4. Listar Matrículas");
                System.out.println("5. Sistema de Buscas");
                System.out.println("0. Sair");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> menuDeInsercao();
                    case 2 -> matricular();
                    case 3 -> {
                        listarAlunos();
                        listarCursos();
                    }
                    case 4 -> listarMatriculas();
                    case 5 -> sistemaBuscas();
                    case 0 -> isUsando = false;
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
            }
            catch (InputMismatchException e) {
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
            }
            catch (InputMismatchException e) {
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

        System.out.println("\nCadastro dos ALUNOS");
        while (nome.isBlank()) {
            System.out.print("Digite o NOME do Aluno: ");
            nome = scanner.nextLine();
        }

        while (email.isBlank() || !email.contains("@") || !email.contains(".com")) {
            System.out.print("Digite o EMAIL do Aluno: ");
            email = scanner.nextLine();
        }

        boolean isEscrevendo = true;
        while (isEscrevendo) {
            try {
                System.out.println("\nData de Nascimento");

                int dia = 0;
                while (dia > 31 || dia < 1) {
                    System.out.print("Digite o DIA (01): ");
                    dia = scanner.nextInt();
                }

                int mes = 0;
                while (mes > 12 || mes <1) {
                    System.out.print("Digite o MES (01): ");
                    mes = scanner.nextInt();
                }

                int ano = 0;
                while (ano > 2025 || ano < 1900) {
                    System.out.print("Digite o ANO (2001): ");
                    ano = scanner.nextInt();
                }

                dataNascimento = ano + "-" + mes + "-" + dia;
                isEscrevendo = false;

            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void menuDeInsercaoCursos(){
        scanner.nextLine();
        String nome = "";
        String descricao = "";
        int cargaHoraria = 0;

        System.out.println("\nCadastro dos CURSOS");
        while (nome.isBlank()) {
            System.out.print("Digite o NOME do Curso: ");
            nome = scanner.nextLine();
        }

        while (descricao.isBlank()) {
            System.out.print("Digite o DESCRIÇÃO do Curso: ");
            descricao = scanner.nextLine();
        }

        boolean isEscrevendo = true;
        while (isEscrevendo) {
            try {
                System.out.print("Digite a CARGA HORÁRIA do Curso: ");
                cargaHoraria = scanner.nextInt();
                isEscrevendo = false;

            } catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida, Tente novamente.\n");
                scanner.nextLine();
            }
        }

        inserirCurso(nome, descricao, cargaHoraria);
    }

    public static void inserirCurso(String nome, String descricao, Integer cargaHoraria){
        String sql = "INSERT INTO curso (cargaHoraria, descricao, name) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, cargaHoraria);
            statement.setString(2, descricao);
            statement.setString(3, nome);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void matricular() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO matricula (dataMatricula, curso_id, aluno_id) VALUES ('23/05/2025', 1, 1)";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarAlunos() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM aluno";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Alunos\n");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String dataNascimento = String.valueOf(resultSet.getDate("dataNascimento"));
                System.out.println("ID: " + id + " / Nome: " + nome + " / Email: " + email + " / Data de Nascimento: " + dataNascimento);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarCursos() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM curso";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Cursos\n");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("name");
                int cargaHoraria = resultSet.getInt("cargaHoraria");
                String descricao = resultSet.getString("descricao");
                System.out.println("ID: " + id + " / Nome: " + nome + " / Descrição: " + descricao + " / Carga Horária: " + cargaHoraria);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarMatriculas(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "SELECT matricula.id, matricula.curso_id, matricula.aluno_id, matricula.dataMatricula, curso.name, aluno.nome " +
                    "FROM matricula " +
                    "LEFT JOIN curso ON matricula.curso_id = curso.id " +
                    "LEFT JOIN aluno ON matricula.aluno_id = aluno.id";

            ResultSet resultSet = statement.executeQuery(sql);
            System.out.print("\nLista de Matriculas\n");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dataMatricula = String.valueOf(resultSet.getDate("dataMatricula"));
                String cursoName = resultSet.getString("name");
                String alunoNome = resultSet.getString("nome");
                System.out.println("ID: " + id + " / Data da Matrícula: " + dataMatricula + " / Nome do Curso: " + cursoName + " / Nome do Aluno: " + alunoNome);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sistemaBuscas(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}