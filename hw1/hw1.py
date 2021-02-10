#print("How many names would you like to print?")
num_of_names = input()
nameList = []
for i in range(int(num_of_names)):
   # print("Please enter name " + str(i+1))
    nameList.append(input())
for i in range(int(num_of_names)):
    print("Hello, " + nameList[i] + "!")
