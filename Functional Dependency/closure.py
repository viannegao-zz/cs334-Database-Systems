'''
Created on Oct 1, 2016

@author: vianne
'''
from itertools import chain, combinations
class fd:
    def __init__(self,lhs,rhs):
        self.lhs = frozenset(lhs)
        self.rhs = frozenset(rhs)
    def __str__(self):
        return str(self.lhs) +'-->' + str(self.rhs)
    def __eq__(self,other):
        return (self.lhs == other.lhs) & (self.rhs == other.rhs)
    def __hash__(self):
        return hash(self.lhs) * hash(self.rhs)
    def isTrivial(self):
        return self.lhs >= self.rhs


class relation:
    def __init__(self, att, allfds, fd1, fd2):
        self.attributes = att
        self.allfds =[allfds]
        self.fd1 = fd1
        self.fd2 = fd2
        
class closure:
    def __init__(self,attr,given_fds):
        self.attr = attr
        self.given_fds = set()
        self.clsr = set()
        for i in range(0,len(given_fds)):
            self.given_fds.add(getFD(given_fds[i]))
    #generates the closure of the given fd
    def getclosure(self):
        #add trivial fds to the set of all known fds 
        known_fds = self.given_fds.union(usereflexivity(self.attr))
        #repeat using augmentation and transitivity until the closure does not change anymore
        done = False;
        while done == False:
            all_fds = useaugmentation(known_fds,powerset(self.attr))
            all_fds = usetransitivity(all_fds)
            done = len(all_fds)==len(known_fds)
            known_fds = all_fds
        self.clsr = known_fds
        
    def __str__(self):
        toPrint = []
        self.getclosure()
        for f in self.clsr:
            toPrint.append(str(f))
        return str(toPrint)
       
def getFD (fd_id):
    a_fd = fd(fd_id[0],fd_id[1])
    return a_fd       

#generate a list of all possible combinations of attributes in a set
def powerset(a_set):
    return list(chain.from_iterable(combinations(a_set,a) for a in range (1, len(a_set)+1)))

#generate a set of trivial fd for given attributes   
def usereflexivity(r):
    all_ref = set()
    for i in powerset(r):
        for j in powerset(i):
            all_ref.add(fd(i,j))
    return all_ref

#generate a set of augmented fd for the set of fiven fd
#Augmentation: if X -> Y, then XZ -> YZ
#f is a set of fd, PS is the powerset of all attributes in the schema
def useaugmentation(f,PS):
    augmented = set()
    for i in f:
        for j in PS:
            augmented.add(fd(i.lhs.union(j),i.rhs.union(j)))
    return augmented

#generate a set of fds derived from the transitivity rule
#Transitivity: if X -> Y, and Y -> Z, then X -> Z
#param f: set of fd
def usetransitivity(f):
    trans = set()
    for i in f:
        for j in f:
            if i.rhs == j.lhs:
                trans.add(fd(i.lhs,j.rhs))
    return f.union(trans)

 
def main():
    #attributes = [1,2,3,4,5]
    fd1 = ([1],[2])
    fd2 = ([2],[2])
    fd3 = ([1],[1]) #tuples of lists
    allfds =[fd1,fd2,fd3] #list of tuples of lists
#     test = closure(attributes, allfds)
#     test.getclosure(attributes, allfds)
    print(closure([1,2,3],allfds))
    #print (test.attributes, test.allfds, test.fd1, test.fd2)
    #print powerset(attributes)
    #test2 = fd(fd2[0],fd2[1])
    #print test2.lhs, test2.rhs

main()