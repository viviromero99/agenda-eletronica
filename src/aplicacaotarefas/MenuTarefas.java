package aplicacaotarefas;

import utils.ConsoleManager;
import utils.enums.Alinhamento;

import java.io.Console;
import java.util.Scanner;

public class MenuTarefas {

    public static void rodarMenu() {
        int opcao = -1;
        do {
            ConsoleManager.imprimirLinhaFormatada("LISTA DE TAREFAS", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("----------------", Alinhamento.CENTRO);
            ConsoleManager.imprimirLinhaFormatada("(1) - Listar Tarefas", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(2) - Adicionar Tarefa", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(3) - Alterar Dados de uma Tarefa", Alinhamento.ESQUERDA);
            ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);

            opcao = ConsoleManager.readOpcaoMenu();
            ConsoleManager.novaLinha(3);

            switch (opcao) {
                case 1:
                    MenuTarefas.listarTarefas();
                    break;
                case 2:
                    MenuTarefas.adicionarTarefa();
                    break;

                case 3:
                    MenuTarefas.alterarDadosTarefa();
                    break;
            }
            ConsoleManager.novaLinha();
        } while (opcao != 0);
    }

    private static void listarTarefas() {
        ConsoleManager.novaLinha();
        DBTarefas.imprimirTarefas();
    }

    private static void adicionarTarefa() {
        ConsoleManager.imprimirLinhaFormatada("---NOVA TAREFA---", Alinhamento.CENTRO);
        ConsoleManager.imprimirLinhaFormatada("TAREFA:: ", Alinhamento.ESQUERDA, false);
        String novaTarefa = ConsoleManager.lerLinha();
        ConsoleManager.imprimirLinhaFormatada("HORARIO(hh:mm):: ", Alinhamento.ESQUERDA, false);
        String novoHorario = ConsoleManager.lerLinha();
        ConsoleManager.imprimirLinhaFormatada("DATA(aaaa-mm-dd):: ", Alinhamento.ESQUERDA, false);
        String novaData = ConsoleManager.lerLinha();
        DBTarefas.inserirTarefa(novaTarefa, novoHorario, novaData);
        ConsoleManager.imprimirLinhaFormatada("---TAREFA ADICIONADA---", Alinhamento.CENTRO);
    }

    private static void alterarDadosTarefa() {
        ConsoleManager.imprimirLinhaFormatada("---ALTERANDO DADOS DE UMA TAREFA---", Alinhamento.CENTRO);
        ConsoleManager.novaLinha();
        DBTarefas.imprimirTarefas();
        ConsoleManager.novaLinha();
        ConsoleManager.imprimirLinhaFormatada("DIGITE O ID DA TAREFA A SER ALTERADA:: ", Alinhamento.ESQUERDA, false);
        String id = ConsoleManager.lerLinha();
        int tarefa_existe = DBTarefas.buscaTarefaPorId(id);

        if (tarefa_existe == 0) {
            ConsoleManager.imprimirLinhaFormatada("Nao existe nenhuma tarefa com esse id", Alinhamento.ESQUERDA);
            return;
        }
        ConsoleManager.novaLinha(2);

        ConsoleManager.imprimirLinhaFormatada("(1) - Alterar Tarefa", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(2) - Alterar Horario", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(3) - Alterar Data", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(4) - Marcar Tarefa Como Concluida", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(5) - Deletar Tarefa", Alinhamento.ESQUERDA);
        ConsoleManager.imprimirLinhaFormatada("(0) - Sair", Alinhamento.ESQUERDA);
        ConsoleManager.novaLinha();

        int opcao = ConsoleManager.readOpcaoMenu();
        switch (opcao) {
            case 1:
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO TAREFA---", Alinhamento.CENTRO);
                ConsoleManager.imprimirLinhaFormatada("NOVA TAREFA:: ", Alinhamento.ESQUERDA, false);
                String novaTarefa = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                DBTarefas.alterarTarefa(id, novaTarefa);
                ConsoleManager.imprimirLinhaFormatada("---TAREFA ALTERADA---", Alinhamento.CENTRO);
                break;

            case 2:
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO HORARIO---", Alinhamento.CENTRO);
                ConsoleManager.imprimirLinhaFormatada("NOVO HORARIO:: ", Alinhamento.ESQUERDA, false);
                String novoHorario = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                DBTarefas.alterarHorario(id, novoHorario);
                ConsoleManager.imprimirLinhaFormatada("---HORARIO ALTERADO---", Alinhamento.CENTRO);
                break;

            case 3:
                ConsoleManager.novaLinha();
                ConsoleManager.imprimirLinhaFormatada("---ALTERANDO A DATA---", Alinhamento.CENTRO);
                ConsoleManager.imprimirLinhaFormatada("NOVA DATA:: ", Alinhamento.ESQUERDA, false);
                String novaData = ConsoleManager.lerLinha();
                ConsoleManager.novaLinha();
                DBTarefas.alterarData(id, novaData);
                ConsoleManager.imprimirLinhaFormatada("---DATA ALTERADA---", Alinhamento.CENTRO);
                break;

            case 4:
                ConsoleManager.novaLinha();
                DBTarefas.marcarTarefaConcluida(id);
                ConsoleManager.imprimirLinhaFormatada("---TAREFA CONCLUIDA---", Alinhamento.CENTRO);
                break;

            case 5:
                ConsoleManager.novaLinha();
                DBTarefas.deletarTarefa(Integer.valueOf(id));
                ConsoleManager.imprimirLinhaFormatada("---TAREFA DELETADA---", Alinhamento.CENTRO);
                break;
        }
    }
}
