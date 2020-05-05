import sys
from arvore import lerArvore, isEmpty, inserirFronteira, No, printCaminho

nosLista = lerArvore()

if(sys.argv.__len__() > 2 and sys.argv[1] != '>'):
    noInicial = next(no for no in nosLista if no.nome == sys.argv[1])
    noObjetivo = next(no for no in nosLista if no.nome == sys.argv[2])
else:
    raise "Infome a cidade inicial e a final na chamada do script!"

print("Cidade partida:", noInicial.nome)
print("Cidade destino:", noObjetivo.nome)
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
            print("- Cidade", filho.nome, "já explorado.")
            print()

    print("Nova expanção:", list(map(No.getNome, fronteira)))
    print()
    print("=====================================")
    print()
