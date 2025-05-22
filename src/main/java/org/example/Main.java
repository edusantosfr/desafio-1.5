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

        String url = "jdbc:postgresql://localhost:5432/escola-teach";
        String user = "postgres";
        String password = "host";

        menu();

//            while (resultSet.next()){
//                String nome = resultSet.getString("nome");
//                String email = resultSet.getString("email");
//                System.out.println("Nome: " + nome);
//                System.out.println("Sobrenome: " + email);
//            }
    }

    public static void menu() {
        System.out.println("Bem vindo ao Sistema de Matrículas");
        System.out.println("\nAntes de qualquer coisa, certifique-se de criar o Database no PgAdmin 4");
        System.out.println("Sem ele, não será possível começar a trabalhar.");

        System.out.println("\n//");

        boolean isUsando = true;
        try {
            while (isUsando) {
                System.out.println("\nSistema de Matrícula");
                System.out.println("1. Inserir aluno ou curso");
                System.out.println("2. Matricular");
                System.out.println("3. Listar alunos ou cursos");
                System.out.println("4. Listar Matrículas");
                System.out.println("5. Sistema de Buscas");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> menuDeInsercao();
                    case 2 -> matricular();
                    case 3 -> listarAluAndCur();
                    case 4 -> listarMatriculas();
                    case 5 -> sistemaBuscas();
                    case 0 -> isUsando = false;
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("\nEntrada Inválida, Tente novamente.\n");
            scanner.nextLine();
        }
    }

    public static void menuDeInsercao() {
        boolean isUsando = true;
        try {
            while (isUsando) {
                System.out.println("\nSistema de Matrícula");
                System.out.println("1. Inserir Aluno");
                System.out.println("2. Inserir Curso");
                System.out.print("Digite sua opção aqui: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> inserirAlunos();
                    case 2 -> inserirCurso();
                    case 0 -> isUsando = false;
                    default -> System.out.println("\nDigite uma Opção Válida.\n");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("\nEntrada Inválida, Tente novamente.\n");
            scanner.nextLine();
        }
    }

    public static void inserirAlunos() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inserirCurso(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void matricular() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarAluAndCur() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarMatriculas(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();


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