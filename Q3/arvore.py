import csv


def isEmpty(collection):
    return collection == None or collection.__len__() == 0


def converterDistancia(string):
    if string is '':
        return None
    return int(string)


def getFilhos(distanciasLista):
    filhos = []
    index = 0
    for nodeDistance in distanciasLista:
        if nodeDistance != None and nodeDistance != 0:
            filhos.append(index)
        index += 1
        
    return filhos


def inserirFronteira(novaCidade, filho, fronteira):
    novaCidade.filho = filho
    novaCidade.custo = novaCidade.custoParente(filho)
    print()
    print("- Pai:", novaCidade.filho.nome)
    print("- Custo atual:", novaCidade.custo)
    print("- Custo por estimativa:", novaCidade.calculaCustoEstimativa())
    for no in fronteira:
        # Mantem as fronteiras com a melhor opção no final
        if(no.calculaCustoEstimativa() <= novaCidade.calculaCustoEstimativa()):
            fronteira.insert(fronteira.index(no), novaCidade)
            return
    fronteira.append(novaCidade)


class No:

    custo = None
    filhos = []
    filho = None

    def __init__(self, id, nome, distanciasLista, distanciaReta=None):
        self.id = id
        self.nome = nome
        self.distanciasLista = list(map(converterDistancia, distanciasLista))
        self.filhos = getFilhos(self.distanciasLista)
        self.distanciaReta = distanciaReta

    #def hasPathTo(self, nodeId):
    #    return self.distanciasLista[nodeId] != None

    def distancia(self, no):
        return self.distanciasLista[no.id]

    def calculaCustoEstimativa(self):
        return self.custo + self.distanciaReta

    def getNome(self):
        return self.nome

    def custoParente(self, filho):
        return filho.custo + filho.distancia(self)


def printCaminho(no):
    if(no.filho is None):
        return no.nome
    return f'{printCaminho(no.filho)} -> {no.nome}'


def lerArvore():

    nosLista = []
    nCidades = 0

    with open('arvore.csv') as arvore:
        csvReader = csv.reader(arvore, delimiter=',')
        nLinhas = 0
        for linha in csvReader:
            if nLinhas == 0:
                tipoBusca = linha[0]
                linha.remove(linha[0])
                print(f'{tipoBusca}: {", ".join(linha)}')
            else:
                nomeCidade = linha[0]
                linha.remove(linha[0])
                nosLista.append(No(nLinhas - 1, nomeCidade,  linha))
            nLinhas += 1
        nCidades = nLinhas - 1
        print(f'Numero total de cidades: {nCidades}')
        print()
        arvore.close

    with open('distanciaReta.csv') as distanciaReta:
        csvReader = csv.reader(distanciaReta, delimiter=',')
        nLinhas = 0
        for linha in csvReader:
            if(nLinhas > nCidades):
                raise "Número de distância em reta diferente do número de cidades!"
            nosLista[nLinhas].distanciaReta = int(linha[1])
            nLinhas += 1
        distanciaReta.close

    return nosLista
