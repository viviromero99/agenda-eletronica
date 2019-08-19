package aplicacaotarefas;

import dbmanager.DBConnection;
import utils.ConsoleManager;
import utils.enums.Alinhamento;

import java.io.Console;
import java.sql.*;

public class DBTarefas {

    public static int buscaTarefaPorId(String id) {
        String sql = "SELECT id, data, horario, tarefa FROM tarefas_table WHERE id = \"" + id + "\"";
        int existe = 0;

        try (Connection conn = DBConnection.connect("database\\tarefas.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    existe=1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return existe;
    }

    public static void imprimirTarefas() {
        String sql = "SELECT id, data, horario, tarefa, concluida FROM tarefas_table ORDER BY data, horario ASC";

        try (Connection conn = DBConnection.connect("database\\tarefas.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ConsoleManager.imprimirLinhaFormatada("------------------T A R E F A S------------------", Alinhamento.CENTRO);
                ConsoleManager.imprimirLinhaFormatada("DATA", Alinhamento.CENTRO, 12, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("HORARIO", Alinhamento.CENTRO, 10, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("TAREFA", Alinhamento.CENTRO, 30, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("CONCLUIDA", Alinhamento.CENTRO, 12);
                while (rs.next()) {
                    ConsoleManager.imprimirLinhaFormatada(rs.getString("id"), Alinhamento.CENTRO, 12, false);
                    System.out.print(" | ");
                    ConsoleManager.imprimirLinhaFormatada(rs.getString("data"), Alinhamento.CENTRO, 12, false);
                    System.out.print(" | ");
                    ConsoleManager.imprimirLinhaFormatada(rs.getString("horario"), Alinhamento.CENTRO, 10, false);
                    System.out.print(" | ");
                    ConsoleManager.imprimirLinhaFormatada(rs.getString("tarefa"), Alinhamento.CENTRO, 30, false);
                    System.out.print(" | ");
                    ConsoleManager.imprimirLinhaFormatada(rs.getString("concluida"), Alinhamento.CENTRO, 12, false);
                    ConsoleManager.novaLinha();
                    ConsoleManager.printCharRepeat('-', 207, true);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void inserirTarefa(String tarefa, String horario, String data) {
        String sql = "INSERT INTO tarefas_table(tarefa, horario, data) VALUES(?,?,?)";

        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tarefa);
            pstmt.setString(2, horario);
            pstmt.setString(3, data);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarTarefa(String id_tarefa, String nova_tarefa) {
        String sql = "UPDATE tarefas_table set tarefa = \"" + nova_tarefa + "\" WHERE id = \"" + id_tarefa + "\"";
        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarHorario(String id_tarefa, String novo_horario) {
        String sql = "UPDATE tarefas_table set horario = \"" + novo_horario + "\" WHERE id = \"" + id_tarefa + "\"";
        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarData(String id_tarefa, String nova_data) {
        String sql = "UPDATE tarefas_table set data = \"" + nova_data + "\" WHERE id = \"" + id_tarefa + "\"";
        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void marcarTarefaConcluida(String id_tarefa) {
        String sql = "UPDATE tarefas_table set concluida = 1 WHERE id = \"" + id_tarefa + "\"";
        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarTarefa(int id_tarefa) {
        String sql = "DELETE FROM tarefas_table WHERE id = ?";

        try {
            Connection conn = DBConnection.connect("database\\tarefas.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_tarefa);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
