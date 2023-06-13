import random


def calculate_fitness(chessboard, permutation):
    total_weight = 0
    for row, col in enumerate(permutation):
        total_weight += chessboard[row][col]
        for i in range(row + 1, len(permutation)):
            if abs(row - i) == abs(col - permutation[i]):
                total_weight -= 10
    return total_weight


def generate_initial_population(N, population_size):
    population = []
    for _ in range(population_size):
        permutation = random.sample(range(N), N)
        population.append(permutation)
    return population


def selection(population, chessboard):
    selected = []
    for _ in range(len(population)):
        tournament = random.sample(population, 2)
        fitness_values = [calculate_fitness(chessboard, permutation) for permutation in tournament]
        selected.append(tournament[fitness_values.index(max(fitness_values))])
    return selected


def crossover(parent1, parent2):
    crossover_point = random.randint(1, len(parent1) - 1)
    child = parent1[:crossover_point] + parent2[crossover_point:]
    return child


def mutation(individual, mutation_rate, N):
    if random.random() < mutation_rate:
        pos1, pos2 = random.sample(range(N), 2)
        individual[pos1], individual[pos2] = individual[pos2], individual[pos1]

    duplicates = set([x for x in individual if individual.count(x) > 1])
    while duplicates:
        for i in range(N):
            if individual[i] in duplicates:
                available_values = set(range(N)).difference(individual)
                if available_values:
                    individual[i] = random.choice(list(available_values))
                else:
                    individual[i] = random.randint(0, N-1)
        duplicates = set([x for x in individual if individual.count(x) > 1])

    return individual


def genetic_algorithm(N, population_size, mutation_rate, weights):
    population = generate_initial_population(N, population_size)

    for _ in range(100):
        selected_population = selection(population, weights)

        offspring_population = []
        while len(offspring_population) < population_size:
            parent1, parent2 = random.sample(selected_population, 2)
            child = crossover(parent1, parent2)
            child = mutation(child, mutation_rate, N)
            offspring_population.append(child)

        fitness_values = [calculate_fitness(weights, permutation) for permutation in offspring_population]

        population = [permutation for _, permutation in sorted(zip(fitness_values, offspring_population), reverse=True)]

    return population[0]


def print_chessboard(positions):
    N = len(positions)
    for row in range(N):
        line = ""
        for col in range(N):
            if positions[row] == col:
                line += "Q "
            else:
                line += "- "
        print(line)
    print()


# Define the parameters
N = 8  # Size of the chessboard
population_size = 100  # Number of individuals in the population
mutation_rate = 0.1  # Probability of mutation

# Define the chessboard weights (randomly generated for this example)
weights = [[random.randint(1, 10) for _ in range(N)] for _ in range(N)]

# Run the genetic algorithm and print the solution
solution = genetic_algorithm(N, population_size, mutation_rate, weights)
print("Best solution:")
print_chessboard(solution)
print("Fitness:", calculate_fitness(weights, solution))
