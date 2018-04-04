package abb;

/**
 * Estrutura auxiliar que contém um valor inteiro e dois nós filhos. Um nó folha possui seus dois filhos iguais a null 
 */
public class No 
{
	int valor;
	No noFilhoEsquerda;
	No noFilhoDireita;
	
	public No (int valor)
	{
		this.valor = valor;
		noFilhoEsquerda = null;
		noFilhoDireita = null;
	}	
}
