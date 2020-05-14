import sys
from arvore import lerArvore, isEmpty, inserirFronteira, No, printCaminho

#@authors Diego Arndt & Ewerthon Ricardo Just

nosLista = lerArvore()
origem = input("Qual a sua cidade de partida?   ")
destino = input("Qual a sua cidade destino?   ")
noInicial = next(no for no in nosLista if no.nome == origem)
noObjetivo = next(no for no in nosLista if no.nome == destino)
print()

noInicial.custo = 0

fronteira = [noInicial]
explorado = []

while(True):

    if isEmpty(fronteira):
        raise "Não existe caminho possível!"

    noAtual = fronteira.pop()

    print("Cidade atual:", noAtual.nome)
    print()

    if(noAtual.nome == noObjetivo.nome):
        print("Objetivo encontrado!!!")
        print()
        print("Caminho:", printCaminho(noAtual))
        print("Custo total:", noAtual.custo)
        print()
        exit(0)
    
    Vizinhos=[]
    for filhoId in noAtual.filhos:
        Vizinhos.append(nosLista[filhoId])
                    
    print("Vizinhos:", list(map(No.getNome , Vizinhos)))
    print()

    explorado.append(noAtual)

    for filhoId in noAtual.filhos:
        filho = nosLista[filhoId]
        
        print("Analisando cidade", filho.nome, "...")
        if (filho not in explorado and filho not in fronteira):
            inserirFronteira(filho, noAtual, fronteira)
            print("- Cidade", filho.nome, "incluída na fronteira.")
            print()
        elif (filho in fronteira and filho.custo > filho.custoParente(noAtual)):
            print("- Cidade", filho.nome, "atualizada com um custo menor.")
            fronteira.remove(filho)
            inserirFronteira(filho, noAtual, fronteira)
            print()
        else:
            print("- Cidade", filho.nome, "já explorada.")
            print()

    print("Nova expanção:", list(map(No.getNome, fronteira)))
    print()
    print("=====================================")
    print()
