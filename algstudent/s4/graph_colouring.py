import json
import time

from helper import draw_coloured_map, generate_graph_map

colours = ["red", "blue", "green", "yellow","orange", "purple", "cyan", "magenta", "lime"]

REPETITIONS = 1

def greedy(graph):

    numberOfColours = 0
    usedColours = []
    solution = {}

    for node in graph:

        neighbours = graph[node]
        #print(neighbours)
        neighbourColours = []

        for neighbour in neighbours:
            if (solution.__contains__(str(neighbour))):
                neighbourColours.append(solution[str(neighbour)])

        #print(neighbourColours)
        for colour in colours:
            if (colour not in neighbourColours):
                solution[node] = colour #Set colour different from its neighbours
                if (colour not in usedColours):
                    usedColours.append(colour)
                    numberOfColours += 1
                    print(colour)
                break #Avoid recolouring

        print(solution)
        if (solution[node] == None): 
            return False, numberOfColours
    
    return solution, numberOfColours

start_time = time.time()
for x in range(REPETITIONS):
    if __name__ == "__main__":
        n = 4
        #map = generate_graph_map(n)
        with open('sols/g64.json') as f:
            map = json.load(f)
            f.close()
        #with open('sols/s256.json') as f:
         #   node_colours = json.load(f)
          #  f.close()
        solution, numberOfColours = greedy(map["graph"])

        if solution:
            print("Solution found:", solution)
            #draw_coloured_map(map, solutions)
            print("Number Of Colours Used: " + str(numberOfColours))
            with open('solution.json', 'w') as f:
                json.dump(solution, f)
                f.close()
        else:
            print("Solution not found.")
finish_time = time.time()
print(finish_time-start_time)