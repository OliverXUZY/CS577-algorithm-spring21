from FF import Node, graph

def main():
    numInstance = int(input())  # the number of instance, first input
    resList = []
    for i in range(numInstance):
        numNodeA, numNodeB, numEdge = map(int,input().strip().split()) #(all ints) the number of nodes in A and in B and edges in i-th instance
        g = graph()
        # first add all edges from s and all edges to t
        for nodeIdx in range(numNodeA):
            g.add_edge("s", "A" + str(nodeIdx+1),1)
        for nodeIdx in range(numNodeB):
            g.add_edge("B" + str(nodeIdx+1), "t",1)

        edgeIdx = 0 # count how many nodes have been added to i-th instance
        while edgeIdx < numEdge:
            u, v = input().strip().split()
            g.add_edge("A" + u, "B" + v, 1)
            edgeIdx += 1
        try:
            res = g.FordFulkerson("s", "t")
        except ValueError:
            print("Oops!  That was a value error.  Try again...")
        
        if(res == numNodeA and res == numNodeB):
            res = str(res) +  " Y"
        else:
            res = str(res) +  " N"
        
        resList.append(res)
        
        
    for res in resList:
        print(res)
    
if __name__ == "__main__":
    main()