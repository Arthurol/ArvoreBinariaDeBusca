package abb;

/**
 * Estrutura auxiliar que cont�m um valor inteiro e dois n�s filhos. Um n� folha possui seus dois filhos iguais a null 
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
