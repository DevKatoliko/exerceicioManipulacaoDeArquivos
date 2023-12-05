package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String args[]) {
		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);
		String[] produtos; //Vai conter as caracteristicas de cada produto que é separado por vírgula no documento original
		List<Product> products = new ArrayList();

		System.out.println("Digite o caminho do arquivo:");
		String path = input.nextLine(); //Digita o caminho do arquivo
		System.out.println("digite o nome do arquivo usando \\");
		String archive = input.nextLine();//Digita o nome do arquivo .csv
		try (BufferedReader bfr = new BufferedReader(new FileReader(path + archive))) { //try with resources para realizar o fechameto adequado do BufferedReader e FileReader
			String line = bfr.readLine();
			while (line != null) {
				System.out.println(line);
				produtos = line.split(","); // coloca nos slots do array o conteúdo anterior a virgula
				String name = produtos[0];
				double value = Double.parseDouble(produtos[1]); // Cast para converter o símbolo numérico de String para valor concreto em Double.
				int quantity = Integer.parseInt(produtos[2]); // Mesma situação de cima mais com o int
				Product product = new Product(name, value, quantity);
				products.add(product);
				line = bfr.readLine();
			} // Esse bloco faz a leitura do arquivo .csv e a instanciação de cada produto com base nos dados do arquivo, e adiciona na lista de produtos. 

		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean createFolderOut = new File(path + File.separator + "Out").mkdir(); // Cria o diretório Out
		System.out.println("Pasta criada: " + createFolderOut);
		List<String> finalProducts = new ArrayList();

		for (Product p : products) {
			finalProducts.add(p.toString());
		} // Esse laço de repetição adiciona na lista de Strings a descrição de cada produto conforme o que pede o exercício
		
		File createFileProdutos = new File(path + File.separator +"Out" + File.separator + "summary.csv");
		try {
		boolean creating = createFileProdutos.createNewFile(); // Cria um novo arquivo .csv dentro do diretório Out
		if(!creating) {
			System.out.println("\nOcorreu um erro ao criar o arquivo ou o arquivo já existe");
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try(BufferedWriter brw = new BufferedWriter( new FileWriter(path + File.separator +"Out" + File.separator + "summary.csv"))){
			for(String s : finalProducts) {
			brw.write(s);
			brw.newLine();
			}//Esse bloco escreve no arquivo criado as informações contidas na lista que contém as informações
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Sumary");
		
		try(BufferedReader bfr = new BufferedReader(new FileReader(path + File.separator + "Out" + File.separator + "summary.csv"))){
			String line = bfr.readLine();
			while(line != null) {
				System.out.println(line);
				line = bfr.readLine();
			}//Esse bloco lê o arquivo criado e mostra o seu conteúdo no console
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		input.close();
	}
}
