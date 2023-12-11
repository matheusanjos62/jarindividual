package metodo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.nio.file.StandardOpenOption;

public class Log {
    private static final String CAMINHO_ARQUIVO = "src/main/java/users/";
    private static final int LIMITE_CPU = 60;  // Defina o limite máximo de CPU conforme necessário
    private static final int LIMITE_RAM = 60;  // Defina o limite máximo de RAM conforme necessário

    public static void gerarLog(Double componenteCPU, Double componenteRAM, Double componenteDisco, String nome) throws IOException {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
        String nomeArquivo = dateFormat.format(dataAtual) + "_log.txt";
        String caminhoCompleto = CAMINHO_ARQUIVO + nomeArquivo;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);

        if (Files.exists(Path.of(caminhoCompleto))) {
            adicionarMensagens(caminhoCompleto, timestamp, componenteCPU, componenteRAM, componenteDisco, nome);
        } else {
            criarNovoArquivo(caminhoCompleto, dataAtual);
        }
    }

    private static void adicionarMensagens(String caminhoCompleto, String timestamp, Double componenteCPU, Double componenteRAM, Double componenteDisco, String nome) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(caminhoCompleto), StandardOpenOption.APPEND)) {

            String mensagemSuporte = "Suporte foi solicitado para arrumar a máquina";

            String mensagemConsumo = String.format("O consumo de CPU estourou o máximo sugerido (%d%%). O consumo de RAM atingiu o máximo sugerido (%d%%) de acordo com o nome da máquina.%n", LIMITE_CPU, LIMITE_RAM);

            String dados = String.format("Data/Hora: %s%nComputador: %s %nConsumo CPU: %.2f%nConsumo RAM: %.2f bytes%nConsumo Disco: %.2f GB%n %n",
                    timestamp, nome, componenteCPU, componenteRAM, componenteDisco, mensagemConsumo);

            writer.write(dados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void criarNovoArquivo(String caminhoCompleto, LocalDate dataAtual) {
        try {
            Files.createFile(Path.of(caminhoCompleto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarMotivo(String mensagem) throws IOException {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
        String nomeArquivo = dateFormat.format(dataAtual) + "_log.txt";
        String caminhoCompleto = CAMINHO_ARQUIVO + nomeArquivo;

        if (Files.exists(Path.of(caminhoCompleto))) {
            LocalDateTime dataAtualLogs = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            String dataFormatadaLog = formatter.format(dataAtualLogs);

            String mensagemLog = dataFormatadaLog + mensagem;

            salvarMensagem(caminhoCompleto, mensagemLog);
            System.out.println("criado");
        } else {
            criarNovoArquivo(caminhoCompleto, dataAtual);
            System.out.println("criado nov");
        }
    }

    private static void salvarMensagem(String caminhoCompleto, String mensagem) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(caminhoCompleto), StandardOpenOption.APPEND)) {
            writer.write(mensagem + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}