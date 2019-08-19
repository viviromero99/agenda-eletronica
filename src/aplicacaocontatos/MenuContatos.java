package aplicacaocontatos;

import utils.ConsoleManager;
import utils.enums.Alinhamento;

public class MenuContatos {
    public static void rodarMenu() {

        int opcao = -1;
        do {
            ConsoleManager.imprimirLinhaFormatada("A G E N D A   D E   C O N T A T O S", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("-----------------------------------", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("(1) - Listar Agenda", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(2) - Criar Contato", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(3) - Alterar Dados de um Contato", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(4) - Criar Grupo", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(5) - Alterar Dados de um Grupo", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);

            opcao = ConsoleManager.readOpcaoMenu();

            ConsoleManager.novaLinha(3);

            switch (opcao) {
                case 1:
                    MenuContatos.listarAgenda();
                    break;
                case 2:
                    MenuContatos.criarContato();
                    break;
                case 3:
                    MenuContatos.alterarDadosContato();
                    break;
                case 4:
                    MenuContatos.criarNovoGrupo();
                    break;
                case 5:
                    MenuContatos.alterarDadosDeGrupo();
                    break;
                case 0:
                    break;
                default:
                    ConsoleManager.imprimirLinhaFormatada("Opcao invalida!", Alinhamento.ESQUERDA);
                    break;
            }
            ConsoleManager.novaLinha(3);
        } while (opcao != 0);
    }

    private static void listarAgenda() {
        DBContatos.imprimirContatos();
        ConsoleManager.novaLinha(1);
        DBContatos.imprimirGrupos();
        ConsoleManager.novaLinha(1);
    }

    private static void criarContato() {
        ConsoleManager.imprimirLinhaFormatada("NOVO CONTATO", Alinhamento.CENTRO);
        ConsoleManager.imprimirLinhaFormatada("------------", Alinhamento.CENTRO);
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("NOME:: ", Alinhamento.ESQUERDA, false);
        String nome = ConsoleManager.lerLinha();
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("SOBRENOME:: ", Alinhamento.ESQUERDA, false);
        String sobrenome = ConsoleManager.lerLinha();
        DBContatos.insereCont(nome, sobrenome);
        ConsoleManager.novaLinha();

        int adicionarOutroTelefone = 0;
        do {
            ConsoleManager.imprimirLinhaFormatada("TELEFONE:: ", Alinhamento.ESQUERDA, false);
            String telefone = ConsoleManager.lerLinha();
            DBContatos.insereTelNovoCont(telefone);
            ConsoleManager.novaLinha();
            ConsoleManager.imprimirLinhaFormatada("(1) - Adicionar mais um telefone", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(0) - Nao adicionar mais telefones", Alinhamento.ESQUERDA);
            ConsoleManager.novaLinha();
            adicionarOutroTelefone = ConsoleManager.lerInt();
        } while (adicionarOutroTelefone == 1);

        ConsoleManager.imprimirLinhaFormatada("ENDERECO:: ", Alinhamento.ESQUERDA, false);
        String endereco = ConsoleManager.lerLinha();
        ConsoleManager.imprimirLinhaFormatada("CIDADE:: ", Alinhamento.ESQUERDA, false);
        String cidade = ConsoleManager.lerLinha();
        ConsoleManager.imprimirLinhaFormatada("ESTADO:: ", Alinhamento.ESQUERDA, false);
        String estado = ConsoleManager.lerLinha();
        ConsoleManager.imprimirLinhaFormatada("EMAIL:: ", Alinhamento.ESQUERDA, false);
        String email = ConsoleManager.lerLinha();
        DBContatos.insereEnd(endereco, cidade, estado, email);
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("---Contato adicionado com sucesso---", Alinhamento.CENTRO);
    }

    private static void alterarDadosContato() {
        ConsoleManager.imprimirLinhaFormatada("MODIFICANDO CONTATO", Alinhamento.CENTRO);
        ConsoleManager.imprimirLinhaFormatada("-------------------", Alinhamento.CENTRO);
        ConsoleManager.novaLinha();
        DBContatos.imprimeNomesETelefones();
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("DIGITE O ID DO CONTATO A SER ALTERADO:: ", Alinhamento.ESQUERDA, false);
        int id_contato = ConsoleManager.lerInt();
        int existe_cont = DBContatos.buscaContatoPorId(String.valueOf(id_contato));

        if (existe_cont == 0) {
            ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum contato com essa id", Alinhamento.ESQUERDA);
            return;
        }
        ConsoleManager.novaLinha(2);
        ConsoleManager.imprimirLinhaFormatada("(1) - Alterar Nome do Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(2) - Alterar Sobrenome do Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(3) - Alterar um Telefone do Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(4) - Adicionar um Telefone ao Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(5) - Deletar um Telefone do Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(6) - Alterar Endereco do Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(7) - Deletar Contato", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);
        ConsoleManager.novaLinha(2);

        int opcao = ConsoleManager.readOpcaoMenu();
        switch (opcao) {
            case 1:
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO NOME DO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO NOME:: ", Alinhamento.ESQUERDA, false);
                String novoNome = ConsoleManager.lerLinha();
                DBContatos.alterarNomeContato(id_contato, novoNome);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---NOME DO CONTATO ALTERADO---", Alinhamento.CENTRO);
                break;
            case 2:
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO SOBRENOME DO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO SOBRENOME:: ", Alinhamento.ESQUERDA, false);
                String novoSobrenome = ConsoleManager.lerLinha();
                DBContatos.alterarSobrenomeContato(id_contato, novoSobrenome);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---SOBRENOME DO CONTATO ALTERADO---", Alinhamento.CENTRO);
                break;
            case 3:
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO TELEFONE DO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("ID | TELEFONE", Alinhamento.ESQUERDA);
                DBContatos.imprimeTelefonesDeUmCont(id_contato);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("DIGITE O ID DO TELEFONE A SER ALTERADO:: ", Alinhamento.ESQUERDA, false);
                String idTelefone = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                int existe_tel = DBContatos.buscaTelefonePorId(idTelefone, String.valueOf(id_contato));

                if (existe_tel == 0) {
                    ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum telefone com essa id", Alinhamento.ESQUERDA);
                    return;
                }
                ConsoleManager.imprimirLinhaFormatada("NOVO TELEFONE:: ", Alinhamento.ESQUERDA, false);
                String novoTelefone = ConsoleManager.lerLinha();
                DBContatos.alterarTelefoneContato(idTelefone, novoTelefone);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---TELEFONE DO CONTATO ALTERADO---", Alinhamento.CENTRO);
                break;
            case 4:
                ConsoleManager.imprimirLinhaFormatada("---ADICIONANDO UM TELEFONE AO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("TELEFONE:: ", Alinhamento.ESQUERDA, false);
                String telefone = ConsoleManager.lerLinha();
                DBContatos.insereTel(id_contato, telefone);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---TELEFONE DO CONTATO ADICIONADO---", Alinhamento.CENTRO);
                break;
            case 5:
                ConsoleManager.imprimirLinhaFormatada("---DELETANDO TELEFONE DO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("ID | TELEFONE", Alinhamento.ESQUERDA);
                DBContatos.imprimeTelefonesDeUmCont(id_contato);
                ConsoleManager.imprimirLinhaFormatada("DIGITE A ID DO TELEFONE A SER DELETADO:: ", Alinhamento.ESQUERDA, false);
                int idExTelefone = ConsoleManager.lerInt();
                int existe_extel = DBContatos.buscaTelefonePorId(String.valueOf(idExTelefone), String.valueOf(id_contato));

                if (existe_extel == 0) {
                    ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum telefone com essa id", Alinhamento.ESQUERDA);
                    return;
                }
                DBContatos.deletarTelefoneDeUmContato(idExTelefone);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---TELEFONE DO CONTATO DELETADO---", Alinhamento.CENTRO);
                break;
            case 6:
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO ENDERECO DO CONTATO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO ENDERECO:: ", Alinhamento.ESQUERDA);
                String novoEndereco = ConsoleManager.lerLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVA CIDADE:: ", Alinhamento.ESQUERDA, false);
                String novaCidade = ConsoleManager.lerLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO ESTADO:: ", Alinhamento.ESQUERDA, false);
                String novoEstado = ConsoleManager.lerLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO EMAIL:: ", Alinhamento.ESQUERDA, false);
                String novoEmail = ConsoleManager.lerLinha();
                DBContatos.alterarEnderecoContato(id_contato, novoEndereco, novaCidade, novoEstado, novoEmail);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---ENDERECO DO CONTATO ALTERADO---", Alinhamento.CENTRO);
                break;
            case 7:
                ConsoleManager.novaLinha(2);
                DBContatos.deletarTelefonesContato(id_contato);
                DBContatos.deletarEnderecoContato(id_contato);
                DBContatos.deletarContatoEmGrupo(id_contato);
                DBContatos.deletarContato(id_contato);
                ConsoleManager.imprimirLinhaFormatada("---CONTATO DELETADO---", Alinhamento.CENTRO);
                break;
            default:
                ConsoleManager.novaLinha(2);
                ConsoleManager.imprimirLinhaFormatada("Opcao invalida!", Alinhamento.ESQUERDA);
                break;
        }
    }

    private static void criarNovoGrupo() {
        ConsoleManager.imprimirLinhaFormatada("---NOVO GRUPO---", Alinhamento.CENTRO);
        ConsoleManager.novaLinha(2);
        ConsoleManager.imprimirLinhaFormatada("NOME DO GRUPO:: ", Alinhamento.ESQUERDA, false);
        String novoGrupo = ConsoleManager.lerLinha();
        DBContatos.criarGrupo(novoGrupo);
        ConsoleManager.novaLinha(2);
        int opcao = 0;
        ConsoleManager.imprimirLinhaFormatada("---ADICIONANDO CONTATOS AO GRUPO---", Alinhamento.CENTRO);
        ConsoleManager.novaLinha();
        DBContatos.imprimeNomesETelefones();
        ConsoleManager.novaLinha(2);
        do {

            ConsoleManager.imprimirLinhaFormatada("DIGITE A ID DO CONTATO A SER ADICIONADO:: ", Alinhamento.ESQUERDA, false);
            String novoId = ConsoleManager.lerLinha();
            int existe_cont = DBContatos.buscaContatoPorId(novoId);
            ConsoleManager.novaLinha();
            if (existe_cont == 0) {
                ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum contato com essa id", Alinhamento.ESQUERDA);
                return;
            }
            else {
                int existeContNoGrupo = DBContatos.buscaContatoPorGrupo(novoId, novoGrupo);
                if(existeContNoGrupo == 0)
                    DBContatos.inserirContatoNoGrupoPorId(novoId, novoGrupo);
                else{
                    ConsoleManager.imprimirLinhaFormatada("Ja existe esse contato no grupo", Alinhamento.ESQUERDA);
                    return;
                }
            }
            ConsoleManager.novaLinha();
            ConsoleManager.imprimirLinhaFormatada("---CONTATO ADICIONADO AO GRUPO---", Alinhamento.CENTRO);
            ConsoleManager.novaLinha(2);
            ConsoleManager.imprimirLinhaFormatada("(1) - Adicionar mais um Contato", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(0) - Nao adicionar mais Contatos", Alinhamento.ESQUERDA);
            ConsoleManager.novaLinha();
            opcao = ConsoleManager.lerInt();

        } while (opcao == 1);
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("---GRUPO ADICIONADO A AGENDA---", Alinhamento.CENTRO);
    }

    private static void alterarDadosDeGrupo() {
        ConsoleManager.imprimirLinhaFormatada("---ALTERANDO DADOS DE UM GRUPO---", Alinhamento.CENTRO);
        ConsoleManager.novaLinha(1);
        DBContatos.imprimirGrupos();
        ConsoleManager.novaLinha(1);
        ConsoleManager.imprimirLinhaFormatada("NOME DO GRUPO:: ", Alinhamento.ESQUERDA, false);
        String grupo = ConsoleManager.lerLinha();
        int existe_id = DBContatos.selecionaIdPorGrupo(grupo);

        if (existe_id == -1) {
            ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum grupo com esse nome", Alinhamento.ESQUERDA);
            return;
        }
        ConsoleManager.novaLinha(2);
        ConsoleManager.imprimirLinhaFormatada("(1) - Alterar Nome do Grupo", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(2) - Adicionar um contato ao Grupo", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(3) - Remover um contato do Grupo", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(4) - Deletar Grupo", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);
        ConsoleManager.lerLinha();
        ConsoleManager.novaLinha();

        int opcao = ConsoleManager.readOpcaoMenu();
        switch (opcao) {
            case 1:
                ConsoleManager.imprimirLinhaFormatada("--ALTERANDO NOME DO GRUPO--", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("NOVO NOME GRUPO:: ", Alinhamento.ESQUERDA, false);
                String novoNomeGrupo = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                DBContatos.alterarNomeGrupo(grupo, novoNomeGrupo);
                ConsoleManager.imprimirLinhaFormatada("---NOME DO GRUPO ALTERADO---", Alinhamento.CENTRO);
                break;

            case 2:
                ConsoleManager.imprimirLinhaFormatada("---ADICIONANDO CONTATO AO GRUPO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                DBContatos.imprimeNomesETelefones();
                ConsoleManager.novaLinha(2);
                ConsoleManager.imprimirLinhaFormatada("DIGITE A ID DO CONTATO A SER ADICIONADO:: ", Alinhamento.ESQUERDA, false);
                String novoId = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                int existe_cont = DBContatos.buscaContatoPorId(novoId);

                if (existe_cont == 0) {
                    ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum contato com essa id", Alinhamento.ESQUERDA);
                    return;
                }
                else {
                    int existeContNoGrupo = DBContatos.buscaContatoPorGrupo(novoId, grupo);
                    if(existeContNoGrupo == 0)
                        DBContatos.inserirContatoNoGrupoPorId(novoId, grupo);
                    else{
                        ConsoleManager.imprimirLinhaFormatada("Ja existe esse contato no grupo", Alinhamento.ESQUERDA);
                        return;
                    }
                }
                ConsoleManager.imprimirLinhaFormatada("---CONTATO ADICIONADO AO GRUPO---", Alinhamento.CENTRO);
                break;

            case 3:
                ConsoleManager.imprimirLinhaFormatada("---REMOVENDO CONTATO DO GRUPO---", Alinhamento.CENTRO);
                ConsoleManager.novaLinha();
                DBContatos.imprimeNomesETelefones();
                ConsoleManager.novaLinha(2);
                ConsoleManager.imprimirLinhaFormatada("DIGITE A ID DO CONTATO A SER REMOVIDO:: ", Alinhamento.ESQUERDA, false);
                String excontId = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                int exist_cont = DBContatos.buscaContatoPorId(excontId);

                if (exist_cont == 0) {
                    ConsoleManager.imprimirLinhaFormatada("Nao existe nenhum contato com essa id", Alinhamento.ESQUERDA);
                    return;
                }
                else {
                    int existeContNoGrupo = DBContatos.buscaContatoPorGrupo(excontId, grupo);
                    if(existeContNoGrupo == 10)
                        DBContatos.deletarContatoDoGrupoPorId(excontId, grupo);
                    else{
                        ConsoleManager.imprimirLinhaFormatada("Nao existe esse contato no grupo", Alinhamento.ESQUERDA);
                        return;
                    }

                    DBContatos.deletarContatoDoGrupoPorId(excontId, grupo);
                }
                ConsoleManager.imprimirLinhaFormatada("---CONTATO REMOVIDO DO GRUPO---", Alinhamento.CENTRO);
                break;

            case 4:
                ConsoleManager.novaLinha();
                DBContatos.deletarRelacaoGrupoContato(grupo);
                DBContatos.deletarGrupo(grupo);
                ConsoleManager.imprimirLinhaFormatada("---GRUPO DELETADO---", Alinhamento.CENTRO);
                break;
        }
    }
}
