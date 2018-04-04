package abb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela representação de árvores binárias de busca, utilizando a estrutura de dados auxiliar No. Permite a realização de buscas, inserções e remoções de nós.
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
	 * Método que percorre a árvore em busca de um nó que contenha o valor especificado. 
	 * A busca é encerrada ao achar o nó com valor correspondente (true) 
	 * ou ao percorrer a árvore até uma folha sem achar o nó (false)
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
		/*A saída do loop while acontece quando o nó atual aponta para um valor null. 
		Este caso indica que o último nó percorrido era uma folha
		*/
		return false;
	}
	
	/**
	 * Método que insere um novo nó na árvore, contendo o valor especificado (id). Caso a árvore esteja vazia (raiz == null), o novo nó passa a ser a raiz. 
	 * Caso não esteja, o método percorre a árvore respeitando a regra de inserir valores menores à esquerda e maiores à direita.
	 * O novo nó inserido sempre será uma folha
	 */
	public void inserir (int id)
	{
		No no = new No(id); 
		if (raiz == null) //Árvore vazia
		{
			raiz = no;
			return;
		}
		No noAtual = raiz;
		No noPai = null;
		while (true)
		{
			noPai = noAtual;
			if (id < noAtual.valor) //Se o valor do no a ser inserido for menor do que o do atual nó do percurso
			{				
				noAtual = noAtual.noFilhoEsquerda;
				if (noAtual == null) 
				{
					//Se uma folha é encontrada, esta passa a ser nó pai do novo nó inserido
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
	 * Método que remove da árvore o nó que contém o valor especificado. Existem três casos de remoção: remoção de nó folha, remoção de nó com apenas um filho e remoção de nó com dois filhos.
	 * Cada caso é tratado de forma diferente.
	 */
	public boolean remover (int id)
	{
		No noPai = raiz;
		No noAtual = raiz;
		boolean isLeftChild = false; //Booleano utilizado para dizer se o nó a ser removido é filho esquerdo ou direito de outro nó
		
		while (noAtual.valor != id) //loop que representa a busca na árvore pelo nó a ser removido
		{
			noPai = noAtual;
			if (noAtual.valor > id)
			{
				isLeftChild = true;
				//A busca continua na subárvore esquerda
				noAtual = noAtual.noFilhoEsquerda;
			} else
			{
				isLeftChild = false;
				//A busca continua na subárvore direita
				noAtual = noAtual.noFilhoDireita;
			}
			if (noAtual == null) //Nó null é o filho de um nó folha
			{
				//Chegou a uma folha mas não encontrou o nó procurado. Ele não existe, portanto, nesta árvore.
				return false;
			}
		}
		
		//A saída loop while significa que o nó a ser removido foi encontrado
		
		//Caso 1: Se o nó a ser removido for um nó folha
		if (noAtual.noFilhoEsquerda == null && noAtual.noFilhoDireita == null)
		{
			if (noAtual == raiz)
				raiz = null;
			
			if (isLeftChild == true)
				noPai.noFilhoEsquerda = null;
			
			else
				noPai.noFilhoDireita = null;
		}
		
		//Caso 2.1 : Se o nó a ser removido tem apenas um filho e ele está à esquerda
		else if (noAtual.noFilhoDireita == null)
		{
			if (noAtual == raiz)
				raiz = noAtual.noFilhoEsquerda; 
			//O novo nó raiz da árvore é o filho esquerdo do nó removido
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noAtual.noFilhoEsquerda; 
			//O filho esquerdo do nó atual(a ser removido) passa a ser filho esquerdo do nó pai 
			
			else
				noPai.noFilhoDireita = noAtual.noFilhoEsquerda; 
			//O filho esquerdo do nó atual(a ser removido) passa a ser filho direito do nó pai
		}
		//Caso 2.2 : Se o nó a ser removido tem apenas um filho e ele está à direita
		else if (noAtual.noFilhoEsquerda == null)
		{
			if (noAtual == raiz)
				raiz = noAtual.noFilhoDireita;
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noAtual.noFilhoDireita;
			
			else
				noPai.noFilhoDireita = noAtual.noFilhoDireita;
		} 
		
		//Caso 3: O nó a ser removido possui dois filhos
		else if (noAtual.noFilhoEsquerda != null && noAtual.noFilhoDireita != null)
		{
			/* O sucessor do nó a ser removido deve ser escolhido. 
			Ele é o menor elemento da subárvore à direita do nó a ser removido */
			No noSucessor = getSuccessor(noAtual);
			if (noAtual == raiz)
				raiz = noSucessor;
			
			else if (isLeftChild)
				noPai.noFilhoEsquerda = noSucessor;
			
			else
				noPai.noFilhoDireita = noSucessor;
					
			//o sucessor herda o filho esquerdo do nó removido
			noSucessor.noFilhoEsquerda = noAtual.noFilhoEsquerda;
		}		
		return true;		
	}
	
	/**
	 * Método que encontra o sucessor de um nó a ser removido em sua subárvore direita. O sucessor é o nó de menor valor na subárvore.
	 * Caso o sucessor encontrado possua filho direito, seu pai o herda como seu filho esquerdo.
	 */
	public No getSuccessor (No noRemovido)
	{
		No sucessor = null;
		No paiDoSucessor = null;
		No noAtual = noRemovido.noFilhoDireita;
		
		//busca na subárvore à direita do nó a ser removido pelo sucessor.
		while (noAtual != null)
		{
			paiDoSucessor = sucessor;
			sucessor = noAtual;
			noAtual = noAtual.noFilhoEsquerda; 
		}
		//quando noAtual encontrar valor null, seu pai será o sucessor encontrado na sub-arvore
		
		/*
		 * O sucessor nunca terá filho esquerdo, pois este seria o real sucessor. Porém é possivel que ele possua um filho direito.
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
	 * Método que realiza a impressão no console dos nós da árvore binária de busca 
	 * utilizando o método de percurso In-Ordem ou Ordem simétrica (esquerda, raiz, direita)
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
		System.out.println("1 - Demonstração com valores default \n2 - Inserir/remover nós\n");
		int input = reader.nextInt();
		
		if (input == 1)
		{
			ArvoreBinariaBusca b = new ArvoreBinariaBusca();
			b.inserir(3);b.inserir(8);
			b.inserir(1);b.inserir(4);b.inserir(6);b.inserir(2);b.inserir(10);b.inserir(9);
			b.inserir(20);b.inserir(25);b.inserir(15);b.inserir(16);
			System.out.println("Árvore Original : ");
			b.display(b.raiz);		
			System.out.println("");
			System.out.println("Checar a existência de nó com valor 4 : " + b.busca(4));
			System.out.println("Remoção de nó folha (2) : " + b.remover(2));		
			b.display(raiz);
			System.out.println("\n Remover nó com um filho (4) : " + b.remover(4));		
			b.display(raiz);
			System.out.println("\n Remover nó com dois filhos (10) : " + b.remover(10));		
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
					System.out.println("Valor do nó a ser inserido: ");
					valor = reader.nextInt();
					novaArvore.inserir(valor);
					System.out.println("\nÁrvore: ");
					novaArvore.display(novaArvore.raiz);
					System.out.println("");
				}
				else if (escolha == 2)
				{
					if (novaArvore.raiz != null)
					{
						System.out.println("Valor do nó a ser removido: ");
						valor = reader.nextInt();
						novaArvore.remover(valor);
					}
						System.out.println("\nÁrvore: ");
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

