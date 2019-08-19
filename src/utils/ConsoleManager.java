package utils;

import utils.enums.Alinhamento;

import java.util.Scanner;

public class ConsoleManager {

    private static Scanner scanner = new Scanner(System.in);

    public static void imprimirLinhaFormatada(String linha, Alinhamento alinhamento, int TAM_LINHA, boolean novaLinha) {
        String margemEsquerda = "";

        if (alinhamento == Alinhamento.CENTRO)
            margemEsquerda = fixedWhiteSpaces((TAM_LINHA - linha.length()) / 2);
        else if (alinhamento == Alinhamento.DIREITA)
            margemEsquerda = fixedWhiteSpaces(TAM_LINHA - linha.length());

        if (linha.length() % 2 == 0)
            System.out.print(margemEsquerda + linha + margemEsquerda);
        else
            System.out.print(margemEsquerda + linha + margemEsquerda + " ");
        if (novaLinha)
            System.out.println();
    }

    public static void imprimirLinhaFormatada(String linha, Alinhamento alinhamento) {
        ConsoleManager.imprimirLinhaFormatada(linha, alinhamento, linha.length() + (linha.length() / 2), true);
    }

    public static void imprimirLinhaFormatada(String linha, Alinhamento alinhamento, int TAM_LINHA) {
        ConsoleManager.imprimirLinhaFormatada(linha, alinhamento, TAM_LINHA, true);
    }

    public static void imprimirLinhaFormatada(String linha, Alinhamento alinhamento, boolean novaLinha) {
        ConsoleManager.imprimirLinhaFormatada(linha, alinhamento, linha.length() + (linha.length() / 2), novaLinha);
    }

    public static void novaLinha() {
        System.out.println();
    }

    public static void novaLinha(int n) {
        for (int i = 0; i < n; i++)
            System.out.println();
    }

    public static int readOpcaoMenu() {
        ConsoleManager.imprimirLinhaFormatada("Digite uma opcao: ", Alinhamento.ESQUERDA, false);
        String opcao = ConsoleManager.lerLinha();
        while (!opcao.matches("^\\d+$")) {
            ConsoleManager.imprimirLinhaFormatada("Formato invalido, tente de novo: ", Alinhamento.ESQUERDA, false);
            opcao = scanner.nextLine();
        }
        int resp = Integer.parseInt(opcao);
        return resp;
    }

    public static int lerInt() {
        return Integer.parseInt(ConsoleManager.lerLinha());
    }

    public static String lerLinha() {
        Scanner reader = new Scanner(System.in);
        String result = ConsoleManager.scanner.nextLine();
        return result;
    }

    public static String fixedWhiteSpaces(int length) {
        return String.format("%1$" + length + "s", "");
    }

    public static void printCharRepeat(char a, int n, boolean newLine) {
        for (int i = 0; i < n; i++)
            System.out.print(a);
        if (newLine)
            System.out.println();
    }
}
