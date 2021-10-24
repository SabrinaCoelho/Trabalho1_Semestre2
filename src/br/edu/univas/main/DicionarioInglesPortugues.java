package br.edu.univas.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class DicionarioInglesPortugues {
	
	public static void cadastro(Scanner in, Map dic) {
		Map<String, String> dicio = dic;
		boolean aprov = false;
		int op;
		System.out.println("----------------Cadastrar----------------");
		if(opcaoContinuar(in)) {
			System.out.print("Palavra:");
			String palavra, traducao;
			palavra = aceiteEntrada(in);
			
			for(Entry<String, String> cadastro: dicio.entrySet()) {
				if(cadastro.getKey().equals(palavra)) {
					aprov = true;
					System.out.println(cadastro.getKey() + " -> "+cadastro.getValue());
				}
			}
			
			if(aprov) {
				System.out.println("Palavra já cadastrada. Deseja editar?\n"
						+ "[1] Sim\n"
						+ "[2] Não\n>>");
				op = aceiteOp(in);
				while(op > 2 || op < 1) {
					System.out.print("Opção inválida! Por favor, escolha uma opção do menu.\n>>");
					op = aceiteOp(in);
				}
				if(op == 1) {
					editar(in, dic);
				}
			}
			else {
				System.out.print("Tradução:");
				traducao = aceiteEntrada(in);
				dic.put(palavra, traducao);
			}
		}
	}
	public static void editar(Scanner in, Map dic) {
		
		String hostPal, hostTra;
		System.out.println("------------------Editar-----------------");
		if(opcaoContinuar(in)) {
			System.out.print("Deseja editar:\n"
					+ "[1] Palavra\n"
					+ "[2] Tradução\n>>");
			int opEditar = aceiteOp(in);
			
			while(opEditar > 2 || opEditar < 1) {
				System.out.print("Opção inválida! Por favor, escolha uma opção do menu.\n>>");
				opEditar = aceiteOp(in);
			}
			
			switch(opEditar) {
				case 1:{//editar palavra
					System.out.print("De:");
					hostPal = aceiteEntrada(in);
					if(dic.get(hostPal) == null) {
						System.out.println("Palavra não cadastrada.");
						break;
					}
					hostTra = (String)dic.get(hostPal);
					dic.remove(hostPal);
					
					System.out.print("Para:");
					hostPal = aceiteEntrada(in);
					dic.put(hostPal, hostTra);
					break;
				}
				case 2:{//editar traducao
					System.out.print("Nova tradução atribuída a:");
					hostPal = aceiteEntrada(in);
					if(dic.get(hostPal) == null) {
						System.out.println("Palavra não cadastrada.");
						break;
					}
					System.out.print("Nova tradução:");
					hostTra = aceiteEntrada(in);
					dic.replace(hostPal, dic.get(hostPal),hostTra);
					break;
				}
			}
			
		}
	}
	public static void excluir(Scanner in, Map dic) {
		System.out.println("-----------------Excluir-----------------");
		if(opcaoContinuar(in)) {
			System.out.print("Palavra:");
			String palavra = aceiteEntrada(in);
			if(dic.remove(palavra) == null) {
				System.out.println("Palavra não cadastrada.");
			}
			else {
				dic.remove(palavra);
			}
		}
	}
	public static void procurar(Scanner in, Map dic) {
		System.out.println("-----------------Procurar----------------");
		if(opcaoContinuar(in)) {
			System.out.print("Palavra:");
			String palavra = aceiteEntrada(in);
			int opProcurar;
			
			Map<String, String> dicio = dic;
			
			String silabaNome;
			int contPassagemDeSilab, contSilabaAchada;
			boolean achado = true;
			
			if(dic.get(palavra)== null) {//Caso nao encontrada a palavra, procurar semelhante	
				for(Entry<String, String> cadastro: dicio.entrySet()) {
					contPassagemDeSilab = 0;
					contSilabaAchada = 0;
					palavra = palavra.toLowerCase();
					for(int j = 0; j < cadastro.getKey().length();j++) {	
						if(cadastro.getKey().length() != 1) {
							silabaNome = cadastro.getKey().substring(j, j + 2).toLowerCase();
							if(palavra.contains(silabaNome)) {
								contSilabaAchada++;
							}
							contPassagemDeSilab++;
							if(contSilabaAchada == 2 && palavra.length() <= 5) {
								System.out.println("Palavra similar: "+cadastro.getKey() + " -> "+cadastro.getValue());
								achado = false;
								break;
							}
							else if(contSilabaAchada == 3 && palavra.length() > 5) {
								System.out.println("Palavra similar: "+cadastro.getKey() + " -> "+cadastro.getValue());
								achado = false;
								break;
							}
							if(contPassagemDeSilab + 1 == cadastro.getKey().length()) {
								break;
							}
						}
					}
				}
				if(achado) {
					System.out.println("Não encontrada palavra similar.");
				}
				palavra = palavra.substring(0, 1).toUpperCase()+palavra.substring(1).toLowerCase();
				System.out.print("A palavra \""+palavra+"\" não existe no dicionário. Deseja cadastrar?\n"
						+ "[1] Sim\n"
						+ "[2] Não\n>>");
				opProcurar = aceiteOp(in);
				
				while(opProcurar > 2 || opProcurar < 1) {
					System.out.print("Opção inválida! Por favor, escolha uma opção do menu.\n>>");
					opProcurar = aceiteOp(in);
				}
				if(opProcurar == 1){
					cadastro(in, dic);
				}
			}
			else {
				System.out.println("Palavra: "+palavra+"\nTradução: "+dic.get(palavra));
			}
		}
	}
	
	public static int aceiteOp(Scanner in) {//Verificar se a entrada eh numerica
		String opTexto = in.next();
		boolean chave = false;
	    
		{
		    try {
		        int opNum = Integer.parseInt(opTexto);
		    } catch (NumberFormatException nfe) {
		        chave = true;
		    }
		}
		
	    while(chave) {
			System.out.println("Por favor, digite um número:");
			opTexto = in.next();
			{
			    try {
			        int opNum = Integer.parseInt(opTexto);
			        chave = false;
			    } catch (NumberFormatException nfe) {
			        chave = true;
			    }
			}
		}
	    int n = Integer.parseInt(opTexto);
	    in.nextLine();
		return n;
	}
	
	public static String aceiteEntrada(Scanner in) {//Verificar se a entrada nao esta vazia
		String p, correcao;
		correcao = in.nextLine().strip();
		
		while(correcao.length()==0){
			System.out.print("Por favor, digite uma palavra.\n>>");
			correcao = in.nextLine().strip();
		}
		p = correcao.substring(0, 1).toUpperCase()+correcao.substring(1).toLowerCase();
		return p;
	}
	
	public static boolean opcaoContinuar(Scanner in) {//Confirmar continuacao na opcao
		System.out.print("[1] Continuar\n[2] Cancelar\n>>");
		int op = aceiteOp(in);
		while(op > 2 || op < 1) {
			System.out.print("Opção inválida! Por favor, escolha uma opção do menu.\n>>");
			op = aceiteOp(in);
		}
		if(op == 1) {
			return true;
		}
		return false;
		
	}
	
	public static int menu(Scanner in) {//Mostra todas as acoes do codigo
		System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
		System.out.print("[1] Cadastrar\n"
				+ "[2] Editar\n"
				+ "[3] Excluir\n"
				+ "[4] Procurar\n"
				+ "[5] Encerrar\n>>");
		int op = aceiteOp(in);
		while(op > 5 || op < 1) {
			System.out.print("Opção inválida! Por favor, escolha uma opção do menu.\n>>");
			op = aceiteOp(in);
		}
		return op;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Map<String, String> dicionario = new HashMap<>();
		
		System.out.println("Bem-vindo ao dicionário Inglês-Português!");
		int opcao = menu(in);
		while(opcao != 5){
			switch(opcao) {
				case 1:{
					if(dicionario.size() == 100) {
						break;
					}
					else {
					cadastro(in, dicionario);
					break;
					}
				}
				case 2:{
					editar(in, dicionario);
					break;
				}
				case 3:{
					excluir(in, dicionario);
					break;
				}
				case 4:{
					procurar(in, dicionario);
					break;
				}
			}
			/*
			 * O trecho abaixo comentado imprime as palavras cadastradas a cada passagem no menu
			 */
			/*System.out.println("_________________________________________");
			System.out.println(dicionario.size()+" Palavra(s) cadastrada(s) até o momento:");
			for(Entry<String, String> cadastro: dicionario.entrySet()) {
				System.out.println(cadastro.getKey() + ": "+cadastro.getValue());
			}
			System.out.println("_________________________________________");*/
			if(dicionario.size() == 100){
				System.out.println("Você alcançou o limite de 100 palavras!\n"
						+ "Você agora pode apenas editar ou excluir para adicionar novas palavras.");
			}
			opcao = menu(in);
		}
		System.out.println("Até logo!");
		in.close();
	}
}
