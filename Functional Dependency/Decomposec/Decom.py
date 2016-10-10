'''
Create a function to find the closure of a given set of attributes
fd1 = ([1],[2])
fd2 = ([2,3],[4,5])
allfds = [fd1,fd2]
print(closure([1,2,3,4,5],allfds))
'''
from itertools import chain, combinations

'''
A FD class with each object storing one functional dependency.
@param name: name of the FD (fd1, fd2 etc)
@param lhs: the first string of an fd tuple (known att)
@param rhs: the second string of an fd tuple (inferred att) 
'''
class FD:
    def __init__(self, lhs, rhs):
        self.lhs = frozenset(str(lhs))
        self.rhs = frozenset(str(rhs))
    def __str__(self):
        return str(list(self.lhs)) + '->' +str(list(self.rhs))
    def __eq__(self, other):
        return (self.lhs == other.lhs) & (self.rhs == other.rhs)
    def isTrivial (self):
        return self.rhs <= self.lhs
    def __hash__(self): #Making fd hashable
        return hash(self.lhs) * hash(self.rhs)

class closure:
    def __init__(self, attributes, allfds):
        self.attributes = attributes
        self.givenfds = allfds
        self.myClosure = set()
        self.pset = list(chain.from_iterable(combinations(self.attributes,i) 
                                             for i in range (1, len(self.attributes)+1)))
        done = False
        while done == False:
            numFd = len(self.myClosure)
            self.useAugmentation()
            self.useReflexivity()
            self.useTransitivity()
            done = numFd == len(self.myClosure)
            
    def __str__(self):
        ret = []
        for i in self.myClosure:
            ret.append(str(i))
        return str(ret)
    #def getClosure(self):
    
    def useReflexivity(self):
        temp = set()
        'generate all the trivial FDs'
        for i in self.pset:
            for j in i:
                trivialFd = FD(i,j)
                temp.add(trivialFd)
        self.myClosure = self.myClosure.union(temp)
    
    def useAugmentation(self):
        temp = set()
        for i in self.myClosure:
            for j in self.pset:
                lhs = set(i.lhs).union(j)
                rhs = set(i.rhs).union(j)
                augFd = FD(lhs,rhs)
                temp.add(augFd)
        self.myClosure = self.myClosure.union(temp)
    
    def useTransitivity(self):
        temp = set()
        for i in self.myClosure:
            for j in self.myClosure:
                if i.rhs == j.lhs:
                    transFd = FD(i.lhs, j.rhs)
                    temp.add(transFd)
        self.myClosure = self.myClosure.union(temp)
    
    

# #Find all candidate keys
# def getCandidateKey(attr, clsr):
#     skey = superkeys(attr,clsr)
#     ckey = set()
#     sorted_skey = sorted(skey, lambda x,y:cmp(len(x),len(y)))
#     for k in sorted_skey:
#         addkey = True
#         for c in ckey:
#             if (k <= c):
#                 addkey = False
#         if addkey:
#             ckey.add(k)
#     return ckey

def main():
    fd1 = ([1],[2])
#     fd2 = ([2,3],[4,5]) 
#     fd3 = ([6],[7]) #tuples of lists
    allfds =[fd1] #list of tuples of lists
    print(closure([1,2,3],allfds))
    #print(BCNF([1,2,3],allfds))


main()           
        
        
        
        
        