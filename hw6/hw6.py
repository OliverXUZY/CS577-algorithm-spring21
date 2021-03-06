class CountInversion:
    def __init__(self, List):
        self.listInput = List
    
    def mergeCount(self, l1, l2):
        mergeList = []
        idx1 = 0
        idx2 = 0
        count = 0
        while True:
            if len(l1) == idx1 and len(l2) == idx2: # if all items added to mergeList, return
                return mergeList, count

            add2 = False   # boolean variable indicates whether append item from list 2
            if len(l1) == idx1: 
                add2 = True
            elif  len(l2) > idx2 and l1[idx1] > l2[idx2]:
                add2 = True
                
            # add
            #print("idx1: ",idx1," idx2: ",idx2)
            if(add2):
                mergeList.append(l2[idx2])
                #print("Now add ", l2[idx2], " count is ", len(l1) - idx1)
                count += len(l1) - idx1
                idx2 += 1
            else:
                mergeList.append(l1[idx1])
                idx1 += 1
    
    def countSort(self, L):
        if len(L) == 1:
            return L, 0
        mid = len(L)//2

        l1, c1 = self.countSort(L[:mid])
        l2, c2 = self.countSort(L[mid:])

        # now l1 and l2 are sorted list
        sortL, c = self.mergeCount(l1, l2)

        return sortL, c1 + c2 + c
    
    def getCount(self):
        _, count = self.countSort(self.listInput)
        return count
    
# This class is for reorder bottom list according the top one:
class ReorderList:
    #def __init__(self, L, R):
    #    self.L = L
    #    self.R = R
    def mergeOrder(self, l1, l2, r1, r2): 
        # l1, l2 are top helf list to be merged
        # r1, r2 are bottom half list to be merged
        mergeListTop = []
        mergeListBot = []
        idx1 = 0
        idx2 = 0
        while True:
            if len(l1) == idx1 and len(l2) == idx2: # if all items added to mergeList, return
                return mergeListTop, mergeListBot

            add2 = False   # boolean variable indicates whether append item from list 2
            if len(l1) == idx1: 
                add2 = True
            elif  len(l2) > idx2 and l1[idx1] > l2[idx2]:
                add2 = True
                
            # add
            #print("idx1: ",idx1," idx2: ",idx2)
            if(add2):
                mergeListTop.append(l2[idx2])
                mergeListBot.append(r2[idx2])
                #print("Now add ", l2[idx2], " count is ", len(l1) - idx1)
                
                idx2 += 1
            else:
                mergeListTop.append(l1[idx1])
                mergeListBot.append(r1[idx1])
                idx1 += 1
    def reOrder(self, L, R):
        if len(L) == 1:
            return L, R
        mid = len(L)//2

        l1, r1 = self.reOrder(L[:mid], R[:mid])
        l2, r2 = self.reOrder(L[mid:], R[mid:])

        # now l1 and l2 are sorted list
        l, r = self.mergeOrder(l1, l2, r1, r2)

        return l, r
    
    def getOrder(self):
        l, r = self.reOrder(self.L, self.R)
        return l, r
    

def main():
    Order = ReorderList() # initialize reOrder class
    numInstance = int(input())  # the number of instance, first input
    countList = []
    for i in range(numInstance):
        numNode = int(input())  # the number of elements in the ranking in i-th instance
        
        L = []  # top list
        R = []  # bottom list
        for node in range(numNode):
            L.append(int(input()))
        for node in range(numNode):
            R.append(int(input()))
        
        _,r = Order.reOrder(L,R)  # reorder L and R, the R is what we want
        count = CountInversion(r)
        countList.append(count.getCount())
                
        #print("\n")
    for count in countList:
        print(count)
        
main()