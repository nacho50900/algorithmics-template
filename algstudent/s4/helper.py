import json

import matplotlib.pyplot as plt
import networkx as nx
import random
import math


def generate_graph_map(n):
    # Calculating optimal grid dimensions
    side = math.ceil(math.sqrt(n))
    width, height = side, side

    # Generate unique positions in a grid
    positions = random.sample([(x, y) for x in range(width) for y in range(height)], n)
    nodes = {i: positions[i] for i in range(n)}
    graph = {str(i): set() for i in range(n)}

    # Creating connections while respecting geographical proximity and ensuring connectivity
    connected_nodes = set()
    for i in range(n):
        x1, y1 = nodes[i]
        neighbours = []
        for j in range(n):
            if i != j:
                x2, y2 = nodes[j]
                # Include cardinal and diagonal directions
                # Cardinal: Manhattan distance = 1
                # Diagonals: Euclidean distance <= sqrt(2) and Manhattan distance = 2
                manhattan_dist = abs(x1 - x2) + abs(y1 - y2)
                euclidean_dist = math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2)

                if manhattan_dist == 1 or (euclidean_dist <= math.sqrt(2) and manhattan_dist == 2):
                    neighbours.append(j)

        # Ensure at least one connection
        if neighbours:
            connect_node = random.choice(neighbours)
            graph[str(i)].add(connect_node)
            graph[str(connect_node)].add(i)
            connected_nodes.add(i)
            connected_nodes.add(connect_node)

        # Increase connection density
        num_connections = min(random.randint(0, 8), len(neighbours))
        connections = random.sample(neighbours, num_connections) if len(neighbours) >= num_connections else neighbours

        for c in connections:
            if c not in graph[str(i)]:
                graph[str(i)].add(c)
                graph[str(c)].add(i)

    for node in graph:
        graph[node] = list(graph[node])

    # Check if all nodes are connected
    def dfs(graph, initial_node):
        visited = set()
        stack = [initial_node]

        while stack:
            node = stack.pop()
            if node not in visited:
                visited.add(node)
                for neighbour in graph[str(node)]:
                    if neighbour not in visited:
                        stack.append(neighbour)

        return visited

    visited = dfs(graph, 0)

    # If there are isolated nodes, connect them manually.
    unconnected_nodes = set(range(n)) - visited
    while unconnected_nodes:
        node = unconnected_nodes.pop()
        connect_node = random.choice(list(visited))
        graph[str(node)].append(connect_node)
        graph[str(connect_node)].append(node)
        visited.add(node)

    map = {"nodes": nodes, "graph": graph}
    with open(f'sols/g{n}.json', 'w') as f:
        json.dump(map, f)
        f.close()
    return map


def draw_coloured_map(graph, colours):
    G = nx.Graph()

    # Adding nodes with their positions and edges to the network
    pos = {str(node): (x, y) for node, (x, y) in graph["nodes"].items()}
    for region, neighbours in graph["graph"].items():
        G.add_node(region)
        for neighbour in neighbours:
            G.add_edge(region, str(neighbour))

    # Assign colours to nodes
    color_map = [colours.get(region, "gray") for region in G.nodes()]

    # Drawing the network
    plt.figure(figsize=(8, 8))
    nx.draw(G, pos, node_color=color_map, with_labels=True, node_size=500, font_size=10, font_color='black',
            edge_color='gray')
    plt.show()


if __name__ == "__main__":
    # Generate a map with 100 nodes
    map = generate_graph_map(100)
    print(map)

    node_colours = {}

    # Simple colour assignment for the example
    for node in map["graph"].keys():
        node_colours[node] = "red"

    """
    # We can load the information from JSON files, if you prefer to generate the solution in Java.
    with open('sols/graph.json') as f:
        map = json.load(f)
        f.close()
    with open('sols/solution.json') as f:
        node_colours = json.load(f)
        f.close()
    """

    # View the map
    draw_coloured_map(map, node_colours)

    # Display network information
    print(f"Number of nodes: {len(map['nodes'])}")
    print(f"Connections:")
    for node, neighbours in map["graph"].items():
        print(f"  Node {node}: connected to {neighbours}")
