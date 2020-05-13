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

def inserirFronteira(novoNo, filho, fronteira):
    novoNo.filho = filho
    novoNo.custo = novoNo.custoParente(filho)
    print()
    print("- Pai:", novoNo.filho.nome)
    print("- Custo atual:", novoNo.custo)
    print("- Custo por estimativa:", novoNo.calculaCustoEstimativa())
    for no in fronteira:
        # Mantem as fronteiras com a melhor opção no final
        if(no.calculaCustoEstimativa() <= novoNo.calculaCustoEstimativa()):
            fronteira.insert(fronteira.index(no), novoNo)
            return
    fronteira.append(novoNo)


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
    nNos = 0

    with open('arvore.csv') as arvore:
        csvReader = csv.reader(arvore, delimiter=',')
        nLinhas = 0
        for linha in csvReader:
            if nLinhas == 0:
                tipoBusca = linha[0]
                linha.remove(linha[0])
                print(f'{tipoBusca}: {", ".join(linha)}')
            else:
                nomeNo = linha[0]
                linha.remove(linha[0])
                nosLista.append(No(nLinhas - 1, nomeNo,  linha))
            nLinhas += 1
        nNos = nLinhas - 1
        print(f'Numero total de {tipoBusca}: {nNos}')
        print()
        arvore.close

    with open('distanciaReta.csv') as distanciaReta:
        csvReader = csv.reader(distanciaReta, delimiter=',')
        nLinhas = 0
        for linha in csvReader:
            if(nLinhas > nNos):
                raise "Quantidade de distâncias em linha reta diferente do número de {tipoBusca}!"
            nosLista[nLinhas].distanciaReta = int(linha[1])
            nLinhas += 1
        distanciaReta.close

    return nosLista
