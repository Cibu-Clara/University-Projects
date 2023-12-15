from Grammar import Grammar

if __name__ == '__main__':
    g = Grammar()
    file_name = "../input/g1.txt"
    g.read_from_file(file_name)

    print(str(g))

    if g.check_cfg():
        print(f"The grammar is a CFG\n")
    else:
        print(f"The grammar is not a CFG\n")

    file_name = "../input/g2.txt"
    g.read_from_file(file_name)

    print(str(g))

    if g.check_cfg():
        print(f"The grammar is a CFG\n")
    else:
        print(f"The grammar is not a CFG\n")
