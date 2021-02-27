class CountInversion:
    def __init__(self, strInput):
        self.listInput = [float(i) for i in strInput.strip().split(" ")]
    
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

def main():
    numInstance = int(input())  # the number of instance, first input
    insList = []
    for i in range(numInstance):
        numNode = int(input())  # the number of elements in the ranking in i-th instance
        
        insList.append(CountInversion(input()))
        
        #print("\n")
    for instance in insList:
        print(instance.getCount())
    
main() 