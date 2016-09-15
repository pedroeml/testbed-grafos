Trabalho T2 - Algoritmos e Estrutura de Dados - II - Ci�ncia da Computa��o - FACIN - PUCRS

Autor: Pedro E. M. Lemos

COMO USAR:

Ao importar o arquivo do grafo cole o endere�o de onde est� o arquivo no campo "Nome do Arquivo" da
janela JFileChooser, pois a navega��o est� limitada a arquivos de extens�o "csv".

Cada arquivo CSV funciona corretamente para o algoritmo cujo nome est� escrito no pr�prio arquivo.
Por exemplo, grafo_MST.csv d� para observar corretamente os passos de execu��o se for executado 
com o algoritmo de MST.

Ap�s executar um algoritmo, ter� de recarregar o arquivo se quiser executar outro; por conta de 
alguns algoritmos modificarem o grafo. Ao executar o algoritmo de Dijkstra, ser� requisitado o
nome de um nodo inicial e outro de um nodo final; at� o momento n�o foi feito tratamento de exce��o
para impedir que nomes de nodos n�o existentes ou um caminho entre nodo inicial e final imposs�vel
sendo um grafo direcionado.

N�o est� previs�to o comportamento dos algoritmos caso seja executado com um grafo que n�o foi sugerido.

DESCRI��O DO PROJETO:

O projeto possui tr�s camadas: UI, Neg�cio, Persist�ncia. Na camada de persist�ncia � usado o padr�o DAO
para ler dados de um arquivo texto que formate um grafo e gerar este na camada de neg�cio. Na camada de
neg�cio n�o utilizei nenhum padr�o em especial al�m do Facade. Na camada de interface com usu�rio usei o
padr�o MVC e Observer (para ficar atualizando o componente que exibe grafos na View).

Os algoritmos implementados s�o de caminhamento em largura e profundidade, Dijkstra, MST� e Ordena��o
Topol�gica. O algoritmo de caminho cr�tico n�o foi implementado.

N�o obtive sucesso em exibir os grafos em passo-a-passo nos algoritmos Dijkstra e Ordena��o Topol�gica.
Fui bem sucedido em exibir o passo-a-passo no algoritmo de MST. O m�todo que utilizei para armazenar
as etapas da execu��o no algoritmo de MST � igual ao que usei no algoritmo de Dijkstra e semelhante
ao que usei no algoritmo de Ordena��o Topol�gica. No entanto, s� funcionou com MST por alguma raz�o da
qual ainda n�o tenho certeza�.

Ignore as classes ProgramaTesteAlgoritmos e ProgramaTesteJGraphT na camada de neg�cio. Estas s�o 
apenas classes para ver se os algoritmos e o JGraphT est�o funcionando apropriadamente.

� Algoritmo de Prim, por�m o resultado final da execu��o � igual ao resultado de Kruskal nos slides da disciplina.
� Imprimi os grafos gerados a cada etapa da execu��o para fins de teste. No algoritmo de Ordena��o Topol�gica 
somente era exibido os nodos sem arestas, j� no Dijkstra nada era impresso. Os algoritmos funcionam, s� o passo-
-a-passo que enfrentei dificuldades para desenvolver neste trabalho.