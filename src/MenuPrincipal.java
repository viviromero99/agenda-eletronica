
import aplicacaocontatos.MenuContatos;
import aplicacaotarefas.MenuTarefas;
import utils.ConsoleManager;
import utils.enums.Alinhamento;

public class MenuPrincipal {
    public static void rodarMenu() {
        int opcao = -1;
        while (opcao != 0) {
            ConsoleManager.imprimirLinhaFormatada("A G E N D A   E L E T R O N I C A", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("---------------------------------", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("(1) - Agenda de Contatos", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(2) - Lista de Tarefas", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);

            opcao = ConsoleManager.readOpcaoMenu();
            ConsoleManager.novaLinha(3);

            switch (opcao) {
                case 1:
                    MenuContatos.rodarMenu();
                    break;
                case 2:
                    MenuTarefas.rodarMenu();
                    break;
                case 0:
                    ConsoleManager.imprimirLinhaFormatada("Saindo do programa\n", Alinhamento.ESQUERDA);
                    break;
                default:
                    ConsoleManager.imprimirLinhaFormatada("Opção Inválida\n", Alinhamento.ESQUERDA);
                    break;
            }
        }
    }
}
