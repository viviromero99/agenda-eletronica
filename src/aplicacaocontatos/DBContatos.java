package aplicacaocontatos;

import dbmanager.DBConnection;
import utils.ConsoleManager;
import utils.enums.Alinhamento;

import java.io.Console;
import java.sql.*;

public class DBContatos {

    // funcoes de insercao

    public static void insereCont(String nome, String sobrenome) {
        String sql = "INSERT INTO contatos_table(nome, sobrenome) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insereTelNovoCont(String telefone) {
        int id_cont = selecionaUltimaId("contatos_table");
        String sql = "INSERT INTO telefones_table(id_contato, telefone) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setString(2, telefone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insereTel(int id_cont, String telefone) {
        String sql = "INSERT INTO telefones_table(id_contato, telefone) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setString(2, telefone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insereEnd(String endereco, String cidade, String estado, String email) {
        int id_cont = selecionaUltimaId("contatos_table");
        String sql = "INSERT INTO enderecos_table(id_contato, endereco, cidade, estado, email) VALUES(?,?,?,?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setString(2, endereco);
            pstmt.setString(3, cidade);
            pstmt.setString(4, estado);
            pstmt.setString(5, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void criarGrupo(String nome) {
        String sql = "INSERT INTO grupos_table(nome) VALUES(?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inserirContatoNoGrupo(String nome, String sobrenome, String grupo) {
        int id_cont = selecionaId_ContPorNome(nome, sobrenome);
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "INSERT INTO grupo_contato_table(id_contato, id_grupo) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setInt(2, id_grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inserirContatoNoGrupoPorId(String id, String grupo) {
        int id_cont = Integer.valueOf(id);
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "INSERT INTO grupo_contato_table(id_contato, id_grupo) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setInt(2, id_grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //funcoes de selecao de dados

    public static int selecionaUltimaId(String tabela) {
        String sql = "SELECT MAX(id) AS id FROM \"" + tabela + "\"";
        int id_lastcont = -1;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {

                id_lastcont = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id_lastcont;
    }


    public static int detectaNomeESobrenomeEscolhido(String nome, String sobrenome) {
        String sql = "SELECT nome, sobrenome FROM contatos_table WHERE nome LIKE \"" + nome + "\" AND sobrenome LIKE \"" + sobrenome + "\"";
        int cont = 0;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cont += 1;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cont;
    }

    public static int selecionaId_ContPorNome(String nome, String sobrenome) {
        String sql = "SELECT id, nome, sobrenome FROM contatos_table WHERE nome = \"" + nome + "\" AND sobrenome = \"" + sobrenome + "\"";
        int id_cont = -1;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                id_cont = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id_cont;
    }

    public static int selecionaIdPorGrupo(String grupo) {
        String sql = "SELECT id AS id FROM grupos_table WHERE nome = \"" + grupo + "\"";
        int id_grupo = -1;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                id_grupo = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id_grupo;
    }

    public static int buscaContatoPorId(String id) {
        String sql = "SELECT id FROM contatos_table WHERE id = \"" + id + "\"";
        int existe = 0;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
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

    public static int buscaTelefonePorId(String id, String id_contato) {
        String sql = "SELECT id, id_contato FROM telefones_table WHERE id = \"" + id + "\" AND id_contato = \""+ id_contato + "\"";
        int existe = 0;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
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

    public static int buscaContatoPorGrupo(String id_cont, String grupo) {
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "SELECT id_contato, id_grupo FROM grupo_contato_table WHERE id_contato = \"" + id_cont + "\" AND id_grupo = \""+ id_grupo+"\"";
        int existe = 0;

        try (Connection conn = DBConnection.connect("database\\contatos.db");
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

    //funcoes de alteracao de dados

    public static void alterarNomeContato(int id_cont, String novo_nome) {
        String sql = "UPDATE contatos_table set nome = \"" + novo_nome + "\" WHERE id = \"" + String.valueOf(id_cont) + "\"";
        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarSobrenomeContato(int id_cont, String novo_sobrenome) {
        String sql = "UPDATE contatos_table set sobrenome = \"" + novo_sobrenome + "\" WHERE id = \"" + String.valueOf(id_cont) + "\"";
        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarTelefoneContato(String id_tel, String novo_telefone) {
        String sql = "UPDATE telefones_table set telefone = \"" + novo_telefone + "\" WHERE id = \"" + id_tel + "\"";
        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarEnderecoContato(int id_cont, String novo_end, String nova_cid, String novo_est, String novo_email) {
        String sql = "UPDATE enderecos_table set endereco = \"" + novo_end + "\", cidade = \"" + nova_cid + "\"," +
                " estado = \"" + novo_est + "\", email = \"" + novo_email + "\" WHERE" +
                " id_contato = \"" + String.valueOf(id_cont) + "\"";
        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterarNomeGrupo(String antigo_nome, String novo_nome) {
        String sql = "UPDATE grupos_table set nome = \"" + novo_nome + "\" WHERE nome = \"" + antigo_nome + "\"";
        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //funcoes de impressao de tabelas

    public static void imprimeNomesETelefones() {
        String sql = "SELECT contatos_table.id, nome, sobrenome, telefones_table.telefone FROM contatos_table " +
                "INNER JOIN telefones_table ON telefones_table.id_contato = contatos_table.id ORDER BY nome, sobrenome COLLATE NOCASE ASC";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ConsoleManager.imprimirLinhaFormatada("------------------C O N T A T O S------------------", Alinhamento.CENTRO);

                ConsoleManager.imprimirLinhaFormatada("ID", Alinhamento.CENTRO, 4, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("NOME", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("SOBRENOME", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("TELEFONE", Alinhamento.CENTRO, 16);
                int id_ant = -1;
                while (rs.next()) {
                    int id = rs.getInt("id");

                    if (id != id_ant) {
                        ConsoleManager.imprimirLinhaFormatada(String.valueOf(id), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("nome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("sobrenome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("telefone"), Alinhamento.CENTRO, 16, false);
                        ConsoleManager.novaLinha();
                    }
                    else{
                        ConsoleManager.imprimirLinhaFormatada(String.valueOf(id), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("telefone"), Alinhamento.CENTRO, 16, false);
                        ConsoleManager.novaLinha();
                    }
                    id_ant = id;
                    ConsoleManager.printCharRepeat('-', 61, true);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void imprimeTelefonesDeUmCont(int id_cont) {
        String sql = "SELECT id, telefone FROM telefones_table " +
                "WHERE id_contato = \"" + String.valueOf(id_cont) + "\"";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("id") + "\t" +
                            rs.getString("telefone"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void imprimirContatos() {
        String sql = "SELECT contatos_table.id, contatos_table.nome, contatos_table.sobrenome, telefones_table.telefone," +
                " enderecos_table.endereco, enderecos_table.cidade," +
                " enderecos_table.estado, enderecos_table.email FROM contatos_table INNER JOIN telefones_table " +
                "ON telefones_table.id_contato = contatos_table.id" +
                " INNER JOIN enderecos_table ON enderecos_table.id_contato = contatos_table.id " +
                "ORDER BY contatos_table.nome COLLATE NOCASE";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ConsoleManager.imprimirLinhaFormatada("------------------C O N T A T O S------------------", Alinhamento.CENTRO);
//                ConsoleManager.imprimirLinhaFormatada("ID | NOME | SOBRENOME | TELEFONE | ENDERECO | CIDADE | ESTADO | EMAIL", Alinhamento.ESQUERDA);
                ConsoleManager.imprimirLinhaFormatada("ID", Alinhamento.CENTRO, 4, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("NOME", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("SOBRENOME", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("TELEFONE", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("ENDERECO", Alinhamento.CENTRO, 64, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("CIDADE", Alinhamento.CENTRO, 22, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("ESTADO", Alinhamento.CENTRO, 8, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("EMAIL", Alinhamento.CENTRO, 40);

                int id_ant = -1;
                while (rs.next()) {
                    int id = rs.getInt("id");

                    if (id != id_ant) {

                        ConsoleManager.imprimirLinhaFormatada(String.valueOf(id), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("nome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("sobrenome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("telefone"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("endereco"), Alinhamento.CENTRO, 64, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("cidade"), Alinhamento.CENTRO, 22, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("estado"), Alinhamento.CENTRO, 8, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("email"), Alinhamento.CENTRO, 40, false);
                        ConsoleManager.novaLinha();

                    } else {
                        ConsoleManager.imprimirLinhaFormatada(String.valueOf(id), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("telefone"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 64, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 22, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 8, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 40, false);
                        ConsoleManager.novaLinha();
                    }
                    id_ant = id;
                    ConsoleManager.printCharRepeat('-', 207, true);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void imprimirGrupos() {
        String sql = "SELECT grupos_table.nome AS grupo, contatos_table.id AS id, contatos_table.nome AS nome, contatos_table.sobrenome FROM grupo_contato_table " +
                "INNER JOIN grupos_table ON grupos_table.id = grupo_contato_table.id_grupo INNER JOIN contatos_table ON" +
                " grupo_contato_table.id_contato = contatos_table.id ORDER BY grupos_table.nome, contatos_table.nome COLLATE NOCASE ";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ConsoleManager.imprimirLinhaFormatada("------------------G R U P O S------------------", Alinhamento.CENTRO);

                ConsoleManager.imprimirLinhaFormatada("GRUPO", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("ID", Alinhamento.CENTRO, 4, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("NOME", Alinhamento.CENTRO, 16, false);
                System.out.print(" | ");
                ConsoleManager.imprimirLinhaFormatada("SOBRENOME", Alinhamento.CENTRO, 16);
                String grupo_ant = " ";
                while (rs.next()) {
                    String grupo = rs.getString("grupo");

                    if (grupo.equals(grupo_ant)) {
                        ConsoleManager.imprimirLinhaFormatada("  ", Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("id"), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("nome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("sobrenome"), Alinhamento.CENTRO, 16, false);
                        ConsoleManager.novaLinha();
                    } else {
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("grupo"), Alinhamento.CENTRO, 16, false);;
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("id"), Alinhamento.CENTRO, 4, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("nome"), Alinhamento.CENTRO, 16, false);
                        System.out.print(" | ");
                        ConsoleManager.imprimirLinhaFormatada(rs.getString("sobrenome"), Alinhamento.CENTRO, 16, false);
                        ConsoleManager.novaLinha();
                    }
                    grupo_ant = grupo;
                    ConsoleManager.printCharRepeat('-', 207, true);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //funcoes de remocao de dados

    public static void deletarContato(int id_cont) {
        String sql = "DELETE FROM contatos_table WHERE id = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarTelefonesContato(int id_cont) {
        String sql = "DELETE FROM telefones_table WHERE id_contato = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarEnderecoContato(int id_cont) {
        String sql = "DELETE FROM enderecos_table WHERE id_contato = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarContatoEmGrupo(int id_cont) {
        String sql = "DELETE FROM grupo_contato_table WHERE id_contato = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarTelefoneDeUmContato(int id_tel) {
        String sql = "DELETE FROM telefones_table WHERE id = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_tel);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarContatoDoGrupoPorId(String id, String grupo) {
        int id_cont = Integer.valueOf(id);
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "DELETE FROM grupo_contato_table WHERE id_contato = ? AND" +
                " id_grupo = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setInt(2, id_grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarContatoDoGrupoPorNome(String nome, String sobrenome, String grupo) {
        int id_cont = selecionaId_ContPorNome(nome, sobrenome);
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "DELETE FROM grupo_contato_table WHERE id_contato = ? AND" +
                " id_grupo = ?";

        try {
            Connection conn = DBConnection.connect("database\\contatos.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_cont);
            pstmt.setInt(2, id_grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarGrupo(String grupo) {
        String sql = "DELETE FROM grupos_table WHERE nome = ?";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarRelacaoGrupoContato(String grupo) {
        int id_grupo = selecionaIdPorGrupo(grupo);
        String sql = "DELETE FROM grupo_contato_table WHERE id_grupo = ?";

        try (Connection conn = DBConnection.connect("database\\contatos.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_grupo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
