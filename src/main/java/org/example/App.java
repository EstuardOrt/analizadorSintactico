package org.example;

import picocli.CommandLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import java_cup.runtime.Symbol;

@CommandLine.Command(name = "lexer", mixinStandardHelpOptions = true, version = "0.1")
public class App implements Callable<Integer> {

    @CommandLine.Option(names = "-f", description = "Leer un archivo", required = true)
    private File file;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        if (file != null) {
            BufferedReader lector = Files.newBufferedReader(file.toPath());
            JsonLexer lexer = new JsonLexer(lector);
            parser p = new parser(lexer);

            // Parsear el archivo de entrada
            Symbol parseTree = p.parse();
            Node root = (Node) parseTree.value;

            // Generar el archivo DOT para el árbol de análisis
            String dotFilePath = "parse_tree.dot";
            generateDotFile(root, dotFilePath);

            System.out.println("El archivo DOT ha sido generado en: " + dotFilePath);
        }
        return 0;
    }

    private void generateDotFile(Node root, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("digraph G {\n");
            writeNode(writer, root, "n0", new int[]{0});
            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeNode(FileWriter writer, Node node, String name, int[] index) throws IOException {
        writer.write(name + " [label=\"" + formatLabel(node.getLabel()) + "\"];\n");
        for (Node child : node.getChildren()) {
            String childName = "n" + index[0]++;
            writer.write(name + " -> " + childName + ";\n");
            writeNode(writer, child, childName, index);
        }
    }

    private String formatLabel(String label) {
        switch (label) {
            case "jsonObject":
                return "{}";
            case "jsonPairList":
                return ",";
            case "jsonPair":
                return ":";
            case "jsonArray":
                return "[]";
            case "jsonValueList":
                return "[]";
            case "jsonValue":
                return "Value";
            case "key":
                return "Key";
            case "value":
                return "Value";
            case "STRING":
                return "String";
            case "NUMBER":
                return "Number";
            case "TRUE":
                return "True";
            case "FALSE":
                return "False";
            case "NULL":
                return "Null";
            default:
                return label;
        }
    }
}
