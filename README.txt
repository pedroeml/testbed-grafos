Trabalho T2 - Algoritmos e Estrutura de Dados - II - Ciência da Computação - FACIN - PUCRS

Autor: Pedro E. M. Lemos

COMO USAR:

Ao importar o arquivo do grafo cole o endereço de onde está o arquivo no campo "Nome do Arquivo" da
janela JFileChooser, pois a navegação está limitada a arquivos de extensão "csv".

Cada arquivo CSV funciona corretamente para o algoritmo cujo nome está escrito no próprio arquivo.
Por exemplo, grafo_MST.csv dá para observar corretamente os passos de execução se for executado 
com o algoritmo de MST.

Após executar um algoritmo, terá de recarregar o arquivo se quiser executar outro; por conta de 
alguns algoritmos modificarem o grafo. Ao executar o algoritmo de Dijkstra, será requisitado o
nome de um nodo inicial e outro de um nodo final; até o momento não foi feito tratamento de exceção
para impedir que nomes de nodos não existentes ou um caminho entre nodo inicial e final impossível
sendo um grafo direcionado.

Não está previsíto o comportamento dos algoritmos caso seja executado com um grafo que não foi sugerido.

DESCRIÇÃO DO PROJETO:

O projeto possui três camadas: UI, Negócio, Persistência. Na camada de persistência é usado o padrão DAO
para ler dados de um arquivo texto que formate um grafo e gerar este na camada de negócio. Na camada de
negócio não utilizei nenhum padrão em especial além do Facade. Na camada de interface com usuário usei o
padrão MVC e Observer (para ficar atualizando o componente que exibe grafos na View).

Os algoritmos implementados são de caminhamento em largura e profundidade, Dijkstra, MST¹ e Ordenação
Topológica. O algoritmo de caminho crítico não foi implementado.

Não obtive sucesso em exibir os grafos em passo-a-passo nos algoritmos Dijkstra e Ordenação Topológica.
Fui bem sucedido em exibir o passo-a-passo no algoritmo de MST. O método que utilizei para armazenar
as etapas da execução no algoritmo de MST é igual ao que usei no algoritmo de Dijkstra e semelhante
ao que usei no algoritmo de Ordenação Topológica. No entanto, só funcionou com MST por alguma razão da
qual ainda não tenho certeza².

Ignore as classes ProgramaTesteAlgoritmos e ProgramaTesteJGraphT na camada de negócio. Estas são 
apenas classes para ver se os algoritmos e o JGraphT estão funcionando apropriadamente.

¹ Algoritmo de Prim, porém o resultado final da execução é igual ao resultado de Kruskal nos slides da disciplina.
² Imprimi os grafos gerados a cada etapa da execução para fins de teste. No algoritmo de Ordenação Topológica 
somente era exibido os nodos sem arestas, já no Dijkstra nada era impresso. Os algoritmos funcionam, só o passo-
-a-passo que enfrentei dificuldades para desenvolver neste trabalho.