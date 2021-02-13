# //////////////// DEPTH FIRST SEARCH //////////////////////////
# //
# // Title: DEPTH FIRST SEARCH for graph
# // Course: CS 577 Spring 2021
# //
# // @author Zhuoyan Xu 
# // Email: zxu444@wisc.edu
# //

class mygraph:
    def __init__(self, numNode):
        self.adjList = {}
        self.todo = []
        self.traversal = []
        self.numNode = numNode

    def addNode(self, inputRow):
        inputList = inputRow.split()
        self.adjList[inputList[0]] = inputList[1:]
        
    def DFS(self):
        keys = list(self.adjList.keys())   # get keys of adj list (get nodes in order)
        
        
        NodesNotStepped = [node for node in keys if not node in self.traversal]  ## get nodes not stepped on when we finish exploring one connected component

        while (len(NodesNotStepped) > 0):
            self.todo.append(NodesNotStepped[0])
            
            while len(self.todo) > 0:
                currentNode = self.todo.pop()  ## get current walking node
                self.traversal.append(currentNode)  ## add current node to traversal
            
                for adjNode in reversed(self.adjList[currentNode]):   ## explore the children of current node, (start in reversed order)
                    if not adjNode in self.traversal:   ## make sure node not in visited traversal
                        self.todo.append(adjNode)
                        
                ## now remove duplicates in todo
                #print(self.todo)
                todo = []
                for node in reversed(self.todo):
                    if node not in todo:
                        todo.append(node)
                self.todo = [i for i in reversed(todo)]
                #print(self.todo)
                        
            NodesNotStepped = [node for node in keys if not node in self.traversal]  ## update NodeNotStepped
            
            #print(self.traversal)
            #print(len(NodesNotStepped))

                    
                    
                    
def main():
    numInstance = int(input("Please Enter the number of instance: "))  # the number of instance, first input
    insList = []
    for i in range(numInstance):
        numNode = int(input("Please Enter the number of node in current instance: "))  # the number of nodes in i-th instance
        
        g = mygraph(numNode)
        nodeIdx = 0 # count how many nodes have been added to i-th instance
        while True:
            g.addNode(input())
            #print(g.adjList)
            nodeIdx += 1
            if nodeIdx >= numNode:
                break
        insList.append(g)
        
        #print("\n")
        for instance in insList:
            instance.DFS()
            print(' '.join(instance.traversal))
    
insList = main()
