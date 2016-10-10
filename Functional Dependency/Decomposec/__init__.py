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
        self.lhs = frozenset(list(lhs))
        self.rhs = frozenset(list(rhs))
    def __str__(self):
        return str(self.lhs) + '->' +str(self.rhs)
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
        self.useAugmentation()
        self.useReflexivity()
        self.useTransitivity()
    def __str__(self):
        return self.myClosure
    #def getClosure(self):
    
    def useReflexivity(self):
        'generate all the trivial FDs'
        for i in self.pset:
            for j in i:
                trivialFd = FD(i,j)
                self.myClosure.add(trivialFd)
    
    def useAugmentation(self):
        for i in self.myClosure:
            for j in self.pset:
                lhs = i.lhs.union(j)
                rhs = i.rhs.union(j)
                augFd = FD(lhs,rhs)
                self.myclosure.add(augFd)
    
    def useTransitivity(self):
        for i in self.myClosure:
            for j in self.myClosure:
                if i.rhs == j.lhs:
                    transFd = FD(i.lhs, j.rhs)
                    self.myClosure.add(transFd)
    
def main():
    fd1 = ([1],[2])
#     fd2 = ([2,3],[4,5]) 
#     fd3 = ([6],[7]) #tuples of lists
    allfds =[fd1] #list of tuples of lists
    print(closure([1,2,3],allfds))
    #print(BCNF([1,2,3],allfds))


main()           
        
        
        
        
        