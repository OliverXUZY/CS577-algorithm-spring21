
def binarySearch(finishList, low, high , s):  ## s: start time of job j
    # define non-Existence case
    if(finishList[low] > s):
        return -1
    
    # define base case 
    if(low == high):  # (find it)
        #print(low,high,finishList[low])
        return low
    
    mid = (low + high)//2
    #if(finishList[mid] == s):  ## find it directly
    #    return mid
    if(finishList[mid] > s):
        return binarySearch(finishList, low, mid - 1 , s)
    else:
        if(finishList[mid+1] <= s):
            return binarySearch(finishList, mid+1, high , s)
        else:
            return mid

class WIS:
    def __init__(self, jobList):
        self.sortJob = sorted(jobList, key = lambda x: x[1])
        self.s = list(map(lambda item: item[0],self.sortJob))
        self.f = list(map(lambda item: item[1],self.sortJob))
        self.v = list(map(lambda item: item[2],self.sortJob))

        self._n = len(self.s)
        self.m = [0]*self._n ## total weight of schedule
    
    def schedule(self):
        self.m[0] = self.v[0]

        for j in range(1, self._n): 
            
            i = binarySearch(self.f, 0, j-1, self.s[j])

            if(i == -1):
                self.m[j] = max(self.m[j-1], self.v[j]) 
            else:
                self.m[j] = max(self.m[j-1], self.m[i] + self.v[j]) 
            
        return self.m[-1]


def main():
    numInstance = int(input())  # the number of instance, first input
    res = []
    for i in range(numInstance):
        numJob = int(input())  # the number of jobs in i-th instance
        jobId = 0
        jobs = []
        while jobId < numJob:
            jobs.append(list(map(int, input().split())))
            jobId += 1
        WeightIS = WIS(jobs)
        res.append(WeightIS.schedule())
    for val in res:
        print(val)


if __name__ == "__main__":
    main()

