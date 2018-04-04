package abb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Classe respons�vel pela representa��o de �rvores bin�rias de busca, utilizando a estrutura de dados auxiliar No. Permite a realiza��o de buscas, inser��es e remo��es de n�s.
 *
 */
public class ArvoreBinariaBusca 
{
	public static  No raiz;
	
	public ArvoreBinariaBusca()
	{
		this.raiz = null;
	}
		
	/**
	 * M�todo que percorre a �rvore em busca de um n� que contenha o valor especificado. 
	 * A busca � encerrada ao achar o n� com valor correspondente (true) 
	 * ou ao percorrer a �rvore at� uma folha sem achar o n� (false)
	 */
	public boolean busca (int id)
	{
		No noAtual = raiz;
		while (noAtual != null)
		{
			if (noAtual.valor == id)
				return true;
			
			else if (noAtual.valor > id)
				noAtual = noAtual.noFilhoEsquerda;
			 
			else 
				noAtual = noAtual.noFilhoDireita;
		}
		/*A sa�da do loop while acontece quando o n� atual aponta para um valor null. 
		Este caso indica que o �ltimo n� percorrido era uma folha
		*/
		return false;
	}
	
	/**
	 * M�todo que insere um novo n� na �rvore, contendo o valor especificado (id). Caso a �rvore esteja vazia (raiz == null), o novo n� passa a ser a raiz. 
	 * Caso n�o esteja, o m�todo percorre a �rvore respeitando a regra de inserir valores menores � esquerda e maiores � direita.
	 * O novo n� inserido sempre ser� uma folha
	 */
	public void inserir (int id)
	{
		No no = new No(id); 
		if (raiz == null) //�rvore vazia
		{
			raiz = no;
			return;
		}
		No noAtual = raiz;
		No noPai = null;
		while (true)
		{
			noPai = noAtual;
			if (id < noAtual.valor) //Se o valor do no a ser inserido for menor do que o do atual n� do percurso
			{				
				noAtual = noAtual.noFilhoEsquerda;
				if (noAtual == null) 
				{
					//Se uma folha � encontrada, esta passa a ser n� pai do novo n� inserido
					noPai.noFilhoEsquerda = no;
					return;
				}
			} else
			{
				noAtual = noAtual.noFilhoDireita;
				if(noAtual == null)
				{
					noPai.noFilhoDireita = no;
					return;
				}
			}
		}
	}
	
	/**
	 * M�todo que remove da �rvore o n� que cont�m o valor especificado. Existem tr�s casos de remo��o: remo��o de n� folha, remo��o de n� com apenas um filho e remo��o de n� com dois filhos.
	 * Cada caso � tratado de forma diferente.
	 */
	public boolean remover (int id)
	{
		No noPai = raiz;
		No noAtual = raiz;
		boolean isLeftChild = false; //Booleano utilizado para dizer se o n� a ser removido � filho esquerdo ou direito de outro n�
		
		while (noAtual.valor != id) //loop que representa a busca na �rvore pelo n� a ser removido
		{
			noPai = noAtual;
			if (noAtual.valor > id)
			{
				isLeftChild = true;
				//A busca continua na sub�rvore esquerda
				noAtual = noAtual.noFilhoEsquerda;
			} else
			{
				isLeftChild = false;
				//A busca continua na sub�rvore direita
				noAtual = noAtual.noFilhoDireita;
			}
			if (noAtual == null) //N� null � o filho de um n� folha
			{
				//Chegou a uma folha mas n�o encontrou o n� procurado. Ele n�o existe, portanto, nesta �rvore.
				return false;
			}
		}
		
		//A sa�da loop while significa que o n� a ser removido foi encontrado
		
		//Caso 1: Se o n� a ser removido for um n� folha
		if (noAtual.noFilhoEsquerda == null && noAtual.noFilhoDireita == null)
		{
			if (noAtual == raiz)
				raiz = null;
			
			if (isLeftChild == true)
				noPai.noFilhoEsquerda = null;
			
			else
				noPai.noFilhoDireita = null;
		}
		
		//Caso 2.1 : Se o n� a ser removido tem apenas um filho e ele est� � esquerda
		else if (noAtual.noFilhoDireita == null)
		{
			if (noAtual == raiz)
				raiz = noAtual.noFilhoEsquerda; 
			//O novo n� raiz da �rvore � o filho esquerdo do n� removido
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noAtual.noFilhoEsquerda; 
			//O filho esquerdo do n� atual(a ser removido) passa a ser filho esquerdo do n� pai 
			
			else
				noPai.noFilhoDireita = noAtual.noFilhoEsquerda; 
			//O filho esquerdo do n� atual(a ser removido) passa a ser filho direito do n� pai
		}
		//Caso 2.2 : Se o n� a ser removido tem apenas um filho e ele est� � direita
		else if (noAtual.noFilhoEsquerda == null)
		{
			if (noAtual == raiz)
				raiz = noAtual.noFilhoDireita;
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noAtual.noFilhoDireita;
			
			else
				noPai.noFilhoDireita = noAtual.noFilhoDireita;
		} 
		
		//Caso 3: O n� a ser removido possui dois filhos
		else if (noAtual.noFilhoEsquerda != null && noAtual.noFilhoDireita != null)
		{
			/* O sucessor do n� a ser removido deve ser escolhido. 
			Ele � o menor elemento da sub�rvore � direita do n� a ser removido */
			No noSucessor = getSuccessor(noAtual);
			if (noAtual == raiz)
				raiz = noSucessor;
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noSucessor;
			
			else
				noPai.noFilhoDireita = noSucessor;
					
			//o sucessor herda o filho esquerdo do n� removido
			noSucessor.noFilhoEsquerda = noAtual.noFilhoEsquerda;
		}		
		return true;		
	}
	
	/**
	 * M�todo que encontra o sucessor de um n� a ser removido em sua sub�rvore direita. O sucessor � o n� de menor valor na sub�rvore.
	 * Caso o sucessor encontrado possua filho direito, seu pai o herda como seu filho esquerdo.
	 */
	public No getSuccessor (No noRemovido)
	{
		No sucessor = null;
		No paiDoSucessor = null;
		No noAtual = noRemovido.noFilhoDireita;
		
		//busca na sub�rvore � direita do n� a ser removido pelo sucessor.
		while (noAtual != null)
		{
			paiDoSucessor = sucessor;
			sucessor = noAtual;
			noAtual = noAtual.noFilhoEsquerda; 
		}
		//quando noAtual encontrar valor null, seu pai ser� o sucessor encontrado na sub-arvore
		
		/*
		 * O sucessor nunca ter� filho esquerdo, pois este seria o real sucessor. Por�m � possivel que ele possua um filho direito.
		 * Neste caso, este filho direito passa a ser filho esquerdo do pai do sucessor.
		 */
		if (sucessor != noRemovido.noFilhoDireita)
		{
			paiDoSucessor.noFilhoEsquerda = sucessor.noFilhoDireita;
			sucessor.noFilhoDireita = noRemovido.noFilhoDireita;
		}
		return sucessor;
	}
	
	/**
	 * M�todo que realiza a impress�o no console dos n�s da �rvore bin�ria de busca 
	 * utilizando o m�todo de percurso In-Ordem ou Ordem sim�trica (esquerda, raiz, direita)
	 */
	public void display (No raiz)
	{
		if (raiz != null)
		{
			display(raiz.noFilhoEsquerda);
			System.out.print(" " + raiz.valor);
			display(raiz.noFilhoDireita);
		}
	}
	
	public static void main(String arg[])
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("1 - Demonstra��o com valores default \n2 - Inserir/remover n�s\n");
		int input = reader.nextInt();
		
		if (input == 1)
		{
			ArvoreBinariaBusca b = new ArvoreBinariaBusca();
			b.inserir(3);b.inserir(8);
			b.inserir(1);b.inserir(4);b.inserir(6);b.inserir(2);b.inserir(10);b.inserir(9);
			b.inserir(20);b.inserir(25);b.inserir(15);b.inserir(16);
			System.out.println("�rvore Original : ");
			b.display(b.raiz);		
			System.out.println("");
			System.out.println("Checar a exist�ncia de n� com valor 4 : " + b.busca(4));
			System.out.println("Remo��o de n� folha (2) : " + b.remover(2));		
			b.display(raiz);
			System.out.println("\n Remover n� com um filho (4) : " + b.remover(4));		
			b.display(raiz);
			System.out.println("\n Remover n� com dois filhos (10) : " + b.remover(10));		
			b.display(raiz);
		}
		
		if (input == 2)
		{
			ArvoreBinariaBusca novaArvore = new ArvoreBinariaBusca();
			while(true)
			{
				System.out.println("\n1 - Inserir\n2 - Remover\n3 - Sair");
				int escolha = reader.nextInt();
				int valor;
				if (escolha == 1)
				{
					System.out.println("Valor do n� a ser inserido: ");
					valor = reader.nextInt();
					novaArvore.inserir(valor);
					System.out.println("\n�rvore: ");
					novaArvore.display(novaArvore.raiz);
					System.out.println("");
				}
				else if (escolha == 2)
				{
					if (novaArvore.raiz != null)
					{
						System.out.println("Valor do n� a ser removido: ");
						valor = reader.nextInt();
						novaArvore.remover(valor);
					}
						System.out.println("\n�rvore: ");
						novaArvore.display(novaArvore.raiz);
						System.out.println("");
				}
				else if (escolha == 3)
				{
					System.out.println("Fim!");
					break;
				}
			}	
		}
		
		reader.close();
	}
}

