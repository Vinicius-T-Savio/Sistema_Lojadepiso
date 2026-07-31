package service;

import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Font;
import connection.post;
import topdecor.main;
public class usaPost {
    static post post = new post();
    static PreparedStatement ps;
    static ResultSet rs;
    static Random rd = new Random();
    static main randId = new main();
    static int utilizaRand;
    //usuario
    public static void crudUser(String opcao, int id, String nome, String senha, String cargo, String objAtz){ 
        try {
            if(post.conC()){
            	PreparedStatement ps1;
                switch(opcao){
                    case "insert":
                    	String nome_funcionario;
                    	int id_funcionario;
                    	utilizaRand = randId.gerarId(cargo);
                    	StringBuilder msgFunc = new StringBuilder();
                        ps = post.con.prepareStatement("INSERT INTO usuario(id_user, nome, senha, cargo) VALUES(?,?,?,?)");
                        ps.setInt(1, utilizaRand);
                        ps.setString(2, nome);
                        ps.setString(3, senha);
                        ps.setString(4, cargo);
                        ps.execute();
                        ps.close();
                        ps1 = post.con.prepareStatement("Select * from "+cargo+" WHERE usuario = ?");
                        ps1.setInt(1, utilizaRand);
                        ResultSet rs = ps1.executeQuery();
                        msgFunc.append("=====================\n").append(	"Usuário adicionado com sucesso!\n"	).append("=====================\n");
                        while(rs.next()) {
                        	nome_funcionario = rs.getString("nome");
                        	id_funcionario = rs.getInt(1);
                        	msgFunc.append("Nome do funcionário: ").append(nome).append("\n").append("id do funcionário: ").append(id_funcionario)
                        	.append("\n");
                        }
                        msgFunc.append("Cargo do funcionario:").append(cargo);
                        ps1.close();
                        rs.close();
                        JOptionPane.showMessageDialog(null, msgFunc);
                        break;
                        
                    case "update":
                    	String sqlUpdt = "UPDATE usuario SET";
                    	String atz = JOptionPane.showInputDialog("Digite o novo(a)" +objAtz+ "do usuario:");
                    	switch(objAtz) {
                		case "cargo":
                			sqlUpdt = "UPDATE usuario SET cargo = ? WHERE id_user = ?"; 
                		break;
                		case "senha":
                			sqlUpdt = "UPDATE usuario SET senha = ? WHERE id_user = ?";
                		break;
                		case "nome":
                			sqlUpdt = "UPDATE usuario SET nome = ? WHERE id_user = ?";
                		break;
                	}
                        ps = post.con.prepareStatement(sqlUpdt);
                        ps.setString(1, atz);
                        ps.setInt(2, id);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Informacoes atualizadas!");
                        break;
                        
                    case "delete":
                    	String sqlDelete = "";
                    	switch(cargo) {
                    		case "vendedor":
                    			sqlDelete += "DELETE FROM vendedor WHERE usuario = ?";
                    		break;
                    		case "estoquista":
                    			sqlDelete += "DELETE FROM estoquista WHERE usuario = ?";
                    		break;
                    		case "financeiro":
                    			sqlDelete += "DELETE FROM financeiro WHERE usuario = ?";
                    		break;
                    		case "gestor":
                    			sqlDelete +="DELETE FROM gestor WHERE usuario = ?";
                    		break;
                    	}
                        ps = post.con.prepareStatement(sqlDelete);
                        ps.setInt(1, id);
                        ps.execute();
                        ps.close();
                        ps1 = post.con.prepareStatement("DELETE FROM usuario WHERE id_user = ?");
                        ps1.setInt(1, id);
                        ps1.execute();
                        ps1.close();
                        JOptionPane.showMessageDialog(null, "Usuario deletado do sistema!\nID: " + id);
                        break;
                        
                    case "select":
                    	String sql = "SELECT * FROM usuario";
                        ps = post.con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        
                        StringBuilder msgUser = new StringBuilder();
                        msgUser.append("================================\n")
                               .append("       USUARIOS LOCALIZADOS           \n")
                               .append("================================\n");
                        
                        boolean temUser = false;
                        while(rs.next()){
                            temUser = true;
                            msgUser.append("> NOME: ").append(rs.getString("nome")).append("\n")
                                   .append("  |-- ID: ").append(rs.getInt(1)).append("\n");
                            msgUser.append("cargo: ").append(rs.getString("cargo")).append("\n").append("--------------------------------\n");
                        }
                        
                        rs.close();
                        ps.close();
                        
                        if(temUser) {
                            exibirPainelScroll("Usuarios", msgUser.toString(), 15, 32);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum usuario encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                        
                    default:
                        JOptionPane.showMessageDialog(null, "Operacao invalida");
                        break;
                }
                post.con.close();
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na tabela Usuario: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean loginUser(String nomeUser, String senhaUser, String cargo){// adicionar cargo pra poder mostrar o id dele na mensagem de login
        try {
            if(post.conC()){
                String sql = "SELECT nome, cargo FROM usuario WHERE nome = ? AND senha = ?";           
                ps = post.con.prepareStatement(sql);
                ps.setString(1, nomeUser);
                ps.setString(2, senhaUser);
                rs = ps.executeQuery();
                
                boolean logado = false;
                String cargo1 = "";
                String nome = "";
                
                if(rs.next()){
                    logado = true;
                    nome = rs.getString("nome");
                    cargo1 = rs.getString("cargo");
                }
                rs.close();
                ps.close();
                post.con.close();
                
                if(logado) {
                    JOptionPane.showMessageDialog(null, "Ola " + nome + "!\nAutenticado como: [" + cargo1.toUpperCase() + "]", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados na autenticacao!", "Erro de Conexao", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    //vendedor
    public static void agendarInst(String opcao, int id, String data, String horario, String cor_status, int fk_pedido, int fk_cliente) {
        try {
            if (post.conC()) {
                switch (opcao) {
                    case "insert":
                        utilizaRand = randId.gerarId("agendamento");
                        ps = post.con.prepareStatement("INSERT INTO agendamento(id_agendamento, data_instalacao, hora, status_cor, pedido, cliente) VALUES(?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, utilizaRand);
                        ps.setString(2, data);
                        ps.setString(3, horario);
                        ps.setString(4, cor_status);
                        ps.setInt(5, fk_pedido);
                        ps.setInt(6, fk_cliente);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Agendamento concluído com sucesso!");
                        break;

                    case "update":
                        ps = post.con.prepareStatement("UPDATE agendamento SET data_instalacao = ? WHERE id_agendamento = ?");
                        ps.setString(1, data);
                        ps.setInt(2, id);
                        ps.execute();   
                        ps.close();              
                        JOptionPane.showMessageDialog(null, "Data de instalação atualizada!");
                        break;

                    case "delete":
                        ps = post.con.prepareStatement("DELETE FROM agendamento WHERE id_agendamento = ?"); 
                        ps.setInt(1, id);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Agendamento removido com sucesso!");
                        break;

                    case "select":
                        String sql = "SELECT id_agendamento, data_instalacao, hora, status_cor, pedido, cliente FROM agendamento ORDER BY data_instalacao ASC";
                        ps = post.con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        
                        StringBuilder msgAgend = new StringBuilder();
                        msgAgend.append("========================================\n")
                               .append("       CRONOGRAMA DE INSTALACOES        \n")
                               .append("========================================\n\n");
                        
                        boolean temDados = false;
                        while (rs.next()) {
                            temDados = true;
                            msgAgend.append("> AGENDA ID: ").append(rs.getInt("id_agendamento")).append("\n")
                                    .append("  |-- Data: ").append(rs.getString("data_instalacao")).append("\n")
                                    .append("  |-- Horário: ").append(rs.getString("hora")).append("\n")
                                    .append("  |-- Status: ").append(rs.getString("status_cor")).append("\n")
                                    .append("  |-- Pedido Num: ").append(rs.getInt("pedido")).append("\n")
                                    .append("  |-- Cliente ID: ").append(rs.getInt("cliente")).append("\n")
                                    .append("----------------------------------------\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if (temDados) {
                            exibirPainelScroll("Agendamentos Localizados", msgAgend.toString(), 18, 32);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum agendamento localizado.");
                        }
                        break;
                }
            }
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "Erro no Módulo de Agendamentos: " + e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void gerarPedido(String opcao, String objUpt, int idPedido, int vendedor, boolean aprovacao, boolean statusPedido, 
            int cliente, String dataPedido, double valorTotal, String nomeMaterial) {
        try {
            if (post.conC()) {
                switch (opcao) {
                    case "insert":
                        utilizaRand = randId.gerarId("pedido");
                        String sqlInsert = "INSERT INTO pedido (id_pedido, vendedor, aprovacao, status_pedido, cliente, data_pedido, valor_total, nomematerial) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = post.con.prepareStatement(sqlInsert);
                        ps.setInt(1, utilizaRand);
                        ps.setInt(2, vendedor);
                        ps.setBoolean(3, aprovacao);
                        ps.setBoolean(4, statusPedido);
                        ps.setInt(5, cliente);
                        ps.setString(6, dataPedido);
                        ps.setDouble(7, valorTotal);
                        ps.setString(8, nomeMaterial);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Pedido inserido com sucesso! Código: " + utilizaRand);
                        break;

                    case "select":
                        String sqlSelect = "SELECT id_pedido, vendedor, cliente, data_pedido, valor_total, nomematerial FROM pedido ORDER BY id_pedido DESC";
                        ps = post.con.prepareStatement(sqlSelect);
                        rs = ps.executeQuery();
                        
                        StringBuilder sb = new StringBuilder();
                        sb.append("========================================\n")
                          .append("       REGISTRO DE PEDIDOS EMITIDOS     \n")
                          .append("========================================\n\n");
                        
                        boolean temDados = false;
                        while (rs.next()) {
                            temDados = true;
                            sb.append("> PEDIDO NUM: ").append(rs.getInt("id_pedido")).append("\n")
                              .append("  |-- Vendedor ID: ").append(rs.getInt("vendedor")).append("\n")
                              .append("  |-- Cliente ID: ").append(rs.getInt("cliente")).append("\n")
                              .append("  |-- Emissão: ").append(rs.getDate("data_pedido")).append("\n")
                              .append("  |-- Material: ").append(rs.getString("nomematerial")).append("\n")
                              .append(String.format("  |-- Valor Total: R$ %.2f\n", rs.getDouble("valor_total")))
                              .append("----------------------------------------\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if (temDados) {
                            exibirPainelScroll("Pedidos Encontrados", sb.toString(), 18, 34);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado no sistema.");
                        }
                        break;

                    case "update":
                        String sqlUpt = "";
                        Object valorUpt = null;
                        boolean ehTextoUpt = true;

                        if (objUpt.equals("vendedor")) {
                            sqlUpt = "UPDATE pedido SET vendedor = ? WHERE id_pedido = ?";
                            valorUpt = vendedor;
                            ehTextoUpt = false;
                        } else if (objUpt.equals("cliente")) {
                            sqlUpt = "UPDATE pedido SET cliente = ? WHERE id_pedido = ?";
                            valorUpt = cliente;
                            ehTextoUpt = false;
                        } else if (objUpt.equals("data")) {
                            sqlUpt = "UPDATE pedido SET data_pedido = ? WHERE id_pedido = ?";
                            valorUpt = dataPedido;
                        } else if (objUpt.equals("nome do material")) {
                            sqlUpt = "UPDATE pedido SET nomematerial = ? WHERE id_pedido = ?";
                            valorUpt = nomeMaterial;
                        } else {
                            sqlUpt = "UPDATE pedido SET valor_total = ? WHERE id_pedido = ?";
                            valorUpt = valorTotal;
                            ehTextoUpt = false;
                        }

                        ps = post.con.prepareStatement(sqlUpt);
                        if (ehTextoUpt) {
                            ps.setString(1, (String) valorUpt);
                        } else if (valorUpt instanceof Integer) {
                            ps.setInt(1, (Integer) valorUpt);
                        } else if (valorUpt instanceof Double) {
                            ps.setDouble(1, (Double) valorUpt);
                        }
                        ps.setInt(2, idPedido);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Pedido de id " + idPedido + " atualizado com sucesso!");
                        break;

                    case "delete":
                        String sqlDelete = "DELETE FROM pedido WHERE id_pedido = ?";
                        ps = post.con.prepareStatement(sqlDelete);
                        ps.setInt(1, idPedido);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Pedido de id " + idPedido + " deletado com sucesso");
                        break;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na operação de Pedidos: " + e.getMessage());
        }
    }
    
    public static void gerarOrcamento(String opcao, String objAtz, int id_orcamento, String nome_cliente, int id_cliente, String endereco, String telefone, String nome_materiais, String qtd_materiais, double valor_total) {
        try {
            if (post.conC()) {
                PreparedStatement ps2;
                switch (opcao) {
                    case "insert":
                        id_orcamento = randId.Id("orcamento");
                        id_cliente = randId.Id("cliente");

                        String sqlInsert = "INSERT INTO orcamento(id_orcamento, nome_cliente, id_cliente, endereco, telefone, nome_materiais, qtd_materiais, valor_total) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = post.con.prepareStatement(sqlInsert);
                        ps.setInt(1, id_orcamento);
                        ps.setString(2, nome_cliente);
                        ps.setInt(3, id_cliente);
                        ps.setString(4, endereco);
                        ps.setString(5, telefone);
                        ps.setString(6, nome_materiais);
                        ps.setInt(7, Integer.parseInt(qtd_materiais));
                        ps.setDouble(8, valor_total);
                        ps.execute();
                        ps.close();

                        ps2 = post.con.prepareStatement("INSERT INTO cliente(id_cliente, nome, endereco, telefone) VALUES(?, ?, ?, ?)");
                        ps2.setInt(1, id_cliente);
                        ps2.setString(2, nome_cliente);
                        ps2.setString(3, endereco);
                        ps2.setString(4, telefone);
                        ps2.execute();
                        ps2.close();

                        JOptionPane.showMessageDialog(null, "Orçamento com id " + id_orcamento + " adicionado com sucesso!");
                        break;

                    case "select":
                        String sqlSelect = "SELECT id_orcamento, nome_cliente, id_cliente, endereco, telefone, nome_materiais, qtd_materiais, valor_total FROM orcamento ORDER BY id_orcamento DESC";
                        ps = post.con.prepareStatement(sqlSelect);
                        rs = ps.executeQuery();
                        
                        StringBuilder sb = new StringBuilder();
                        sb.append("========================================\n")
                          .append("       REGISTRO DE ORÇAMENTOS FEITOS     \n")
                          .append("========================================\n\n");
                        
                        boolean temDados = false;
                        while (rs.next()) {
                            temDados = true;
                            sb.append("> PEDIDO NUM: ").append(rs.getInt("id_orcamento")).append("\n")
                              .append("  |-- Cliente ID: ").append(rs.getInt("id_cliente")).append("\n")
                              .append("  |-- Nome do cliente: ").append(rs.getString("nome_cliente")).append("\n")
                              .append("  |-- Telefone do cliente: ").append(rs.getString("telefone")).append("\n")
                              .append("  |-- Endereco do cliente: ").append(rs.getString("endereco")).append("\n")
                              .append("  |-- Nomes dos materiais: ").append(rs.getString("nome_materiais")).append("\n")
                              .append("  |-- Quantidade dos materiais: ").append(rs.getInt("qtd_materiais")).append("\n")
                              .append(String.format("  |-- Valor Total: R$ %.2f\n", rs.getDouble("valor_total")))
                              .append("----------------------------------------\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if (temDados) {
                            exibirPainelScroll("Orcamentos Encontrados", sb.toString(), 18, 34);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum orcamento encontrado no sistema.");
                        }
                        break;

                    case "update":
                        String coluna = "";
                        Object valor = null;
                        boolean ehTexto = true;

                        switch (objAtz) {
                            case "nome do cliente":
                                coluna = "nome_cliente";
                                valor = nome_cliente;
                                break;
                            case "endereço do cliente":
                                coluna = "endereco";
                                valor = endereco;
                                break;
                            case "telefone do cliente":
                                coluna = "telefone";
                                valor = telefone;
                                break;
                            case "nome dos materiais":
                                coluna = "nome_materiais";
                                valor = nome_materiais;
                                break;
                            case "quantidade dos materiais":
                                coluna = "qtd_materiais";
                                valor = Integer.parseInt(qtd_materiais);
                                ehTexto = false;
                                break;
                            case "valor total":
                                coluna = "valor_total";
                                valor = valor_total;
                                ehTexto = false;
                                break;
                            default:
                                return;
                        }

                        if (objAtz.equals("nome do cliente") || objAtz.equals("endereço do cliente") || objAtz.equals("telefone do cliente")) {
                            String colunaCli = objAtz.equals("nome do cliente") ? "nome" : (objAtz.equals("endereço do cliente") ? "endereco" : "telefone");
                            String sqlCli = "UPDATE cliente SET " + colunaCli + " = ? WHERE id_cliente = ?";
                            
                            ps2 = post.con.prepareStatement(sqlCli);
                            ps2.setString(1, (String) valor);
                            ps2.setInt(2, id_cliente);
                            ps2.execute();
                            ps2.close();
                        }

                        String sqlUpdate = "UPDATE orcamento SET " + coluna + " = ? WHERE id_orcamento = ?";
                        ps = post.con.prepareStatement(sqlUpdate);

                        if (ehTexto) {
                            ps.setString(1, (String) valor);
                        } else if (valor instanceof Integer) {
                            ps.setInt(1, (Integer) valor);
                        } else if (valor instanceof Double) {
                            ps.setDouble(1, (Double) valor);
                        }

                        ps.setInt(2, id_orcamento);
                        ps.execute();
                        ps.close();
                        
                        JOptionPane.showMessageDialog(null, "Campo '" + objAtz + "' atualizado com sucesso!");
                        break;

                    case "delete":
                        String sqlDelete = "DELETE FROM orcamento WHERE id_orcamento = ?";
                        ps = post.con.prepareStatement(sqlDelete);
                        ps.setInt(1, id_orcamento);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Orçamento de id " + id_orcamento + " deletado com sucesso");
                        break;
                }
            }
        } catch (NumberFormatException | SQLException e1) {
            JOptionPane.showMessageDialog(null, "Erro na operação de Orçamentos: " + e1.getMessage());
        }
    }
    
    //gestor
    public static void mostrarGestor() { 
        try {
            if (post.conC()) {
                StringBuilder msgGestor = new StringBuilder();
                msgGestor.append("========================================\n")
                         .append("       INDICADORES E ESTOQUE CRITICO    \n")
                         .append("========================================\n\n");
                
                String sqlVendas = "SELECT total FROM vendas_mensais WHERE id_vendas = 1";
                ps = post.con.prepareStatement(sqlVendas);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    msgGestor.append(String.format("[FINANCEIRO] Faturamento Mensal: R$ %.2f\n", rs.getDouble("total")));
                } else {
                    msgGestor.append("[FINANCEIRO] Faturamento Mensal: R$ 0,00\n");
                }
                msgGestor.append("----------------------------------------\n\n");
                rs.close();
                ps.close();
                
                String sqlRotativo = "SELECT nome_prod, quantidade FROM produto_rotativo WHERE quantidade < 10 ORDER BY quantidade ASC";
                ps = post.con.prepareStatement(sqlRotativo);
                rs = ps.executeQuery();
                
                msgGestor.append("[ALERTA] ESTOQUE ROTATIVO EM DEFICIT (< 10 un):\n");
                boolean temRotativo = false;
                while (rs.next()) {
                    temRotativo = true;
                    msgGestor.append(" * ").append(rs.getString("nome_prod")).append(" -> (").append(rs.getInt("quantidade")).append(" un)\n");
                }
                if (!temRotativo) msgGestor.append("   Itens rotativos estaveis.\n");
                msgGestor.append("\n");
                rs.close();
                ps.close();

                String sqlFixo = "SELECT nome_prod, quantidade FROM produto_fixo WHERE quantidade < 10 ORDER BY quantidade ASC";
                ps = post.con.prepareStatement(sqlFixo);
                rs = ps.executeQuery();
                
                msgGestor.append("[ALERTA] ESTOQUE FIXO EM DEFICIT (< 10 un):\n");
                boolean temFixo = false;
                while (rs.next()) {
                    temFixo = true;
                    msgGestor.append(" * ").append(rs.getString("nome_prod")).append(" -> (").append(rs.getInt("quantidade")).append(" un)\n");
                }
                if (!temFixo) msgGestor.append("   Itens fixos estaveis.\n");
                
                rs.close();
                ps.close();
                post.con.close();
                
                exibirPainelScroll("Painel Gerencial", msgGestor.toString(), 20, 47);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar painel do gestor: " + e.getMessage());
        }
    }
    //gestor - estoquista
    public static void crudEstoque(String opcao, String objAtz, int id, String nome, int qtd_fisica, String tipo, String tamanho, String marca, String encaixe, double preco, double volume) {
        try {
            if (post.conC()) {
                StringBuilder msgEstoque = new StringBuilder();
                switch(opcao) {
                    case "tudo":
                        String sql = "SELECT * FROM produto_rotativo ORDER BY nome_prod ASC";
                        ps = post.con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        
                        msgEstoque.append("================================\n")
                                  .append("       CONTROLE DE ESTOQUE     \n")
                                  .append("================================\n\n");
                        
                        boolean temDados = false;
                        while (rs.next()) {
                            temDados = true;
                            msgEstoque.append("> PRODUTO: ").append(rs.getString("nome_prod").toUpperCase()).append("\n")
                                      .append("  |-- ID: ").append(rs.getInt("id_estoque")).append("\n")
                                      .append("  |-- Qtd Fisica: ").append(rs.getInt("quantidade")).append(" un\n")
                                      .append("  |-- Volume p/caixa: ").append(rs.getInt("volume_p_caixa")).append(" m^2\n")
                                      .append("  |-- Tipo: ").append(rs.getString("tipo_prod")).append("\n")
                                      .append(" | Tam: ").append(rs.getString("tamanho")).append("\n")
                                      .append("  |-- Marca: ").append(rs.getString("marca")).append(" | Encaixe: ").append(rs.getString("encaixe")).append("\n")
                                      .append(String.format("  |-- Preco: R$ %.2f\n", rs.getDouble("preco")))
                                      .append("--------------------------------\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if (temDados) {
                            exibirPainelScroll("Estoque Rotativo", msgEstoque.toString(), 20, 34);
                        } else {
                            JOptionPane.showMessageDialog(null, "Estoque vazio.");
                        }
                        break;

                    case "especifico":
                        String sqlEsp = "SELECT * FROM produto_rotativo WHERE nome_prod LIKE ?";
                        ps = post.con.prepareStatement(sqlEsp);
                        ps.setString(1, "%" + nome + "%");
                        rs = ps.executeQuery();
                        
                        msgEstoque.append("================================\n")
                                  .append("       RESULTADO DA BUSCA DE ESTOQUE    \n")
                                  .append("================================\n\n");
                        
                        boolean achou = false;
                        while(rs.next()){
                            achou = true;
                            msgEstoque.append("> PRODUTO: ").append(rs.getString("nome_prod").toUpperCase()).append("\n")
                                      .append("  |-- ID: ").append(rs.getInt("id_estoque")).append("\n")
                                      .append("  |-- Qtd: ").append(rs.getInt("quantidade")).append(" un\n")
                                      .append("  |-- Volume p/caixa: ").append(rs.getInt("volume_p_caixa")).append(" m^2\n")
                                      .append("  |-- Tipo: ").append(rs.getString("tipo_prod")).append("\n")
                                      .append(" | Tam: ").append(rs.getString("tamanho")).append("\n")
                                      .append(String.format("  |-- Preco: R$ %.2f\n", rs.getDouble("preco")))
                                      .append("--------------------------------\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if(achou) {
                            exibirPainelScroll("Busca de Estoque", msgEstoque.toString(), 78, 57);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum produto correspondente localizado.");
                        }
                        break;
                    case "insert":
                    	utilizaRand = randId.gerarId("produto_rotativo");
                        ps = post.con.prepareStatement("INSERT INTO produto_rotativo(id_estoque, nome_prod, quantidade, tipo_prod, tamanho, marca, encaixe, preco, volume_p_caixa) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, utilizaRand);
                        ps.setString(2, nome);
                        ps.setInt(3, qtd_fisica);
                        ps.setString(4, tipo);
                        ps.setString(5, tamanho);
                        ps.setString(6, marca);
                        ps.setString(7, encaixe);
                        ps.setDouble(8, preco);
                        ps.setDouble(9, volume);
                        ps.execute();
                        JOptionPane.showMessageDialog(null, "Produto adicionado ao estoque!\nCodigo ID gerado: " + utilizaRand);
                        ps.close();
                        break;
                    case "update":
                    	String sqlUpdate = "UPDATE produto_rotativo SET ";
                    	String script = " WHERE id_estoque ="+id+"";
                    	String dialog;
                    	int dialog1;
                    	double dialog2;
                    	if(objAtz.equals("nome")) {
                    		dialog = JOptionPane.showInputDialog(null, "Digite o novo nome do produto:");
                    		ps = post.con.prepareStatement(sqlUpdate + "nome_prod = ?" +  script);
                    		ps.setString(1, dialog);
                    		}else if(objAtz.equals("quantidade")) {
                    		dialog1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a nova quantidade do produto:"));
                    		ps = post.con.prepareStatement(sqlUpdate + ""+ objAtz +" = " + dialog1 + script);
                    	}else if(objAtz.equals("tipo")) {
                    		String[] tipos = {"piso laminado","piso vinilico", "piso floorest", "persiana", "rodapé"};
    						tipo = (String) JOptionPane.showInputDialog(null, "escolha o tipo de produto", "Tipos", JOptionPane.INFORMATION_MESSAGE, null, tipos, tipos[0]);
                    		ps = post.con.prepareStatement(sqlUpdate + "tipo = ?" +  script);
                    		ps.setString(1, tipo);
                    	}else if(objAtz.equals("tamanho")) {
                    		dialog = JOptionPane.showInputDialog(null, "Digite o novo tamanho do produto(Largura x Altura x Peças por caixa):");
                    		ps = post.con.prepareStatement(sqlUpdate + ""+ objAtz +" = ?" +  script);
                    		ps.setString(1, dialog);
                    	}else if(objAtz.equals("marca")) {
                    		dialog = JOptionPane.showInputDialog(null, "Digite o nova marca do produto:");
                    		ps = post.con.prepareStatement(sqlUpdate + ""+ objAtz +" = ?" +  script);
                    		ps.setString(1, dialog);
                    	}else if(objAtz.equals("encaixe")) {
                    		dialog = JOptionPane.showInputDialog(null, "Digite o novo encaixe do produto:");
                    		ps = post.con.prepareStatement(sqlUpdate + ""+ objAtz +" = ?" +  script);
                    		ps.setString(1, dialog);
                    	}else if(objAtz.equals("preco")){
                    		dialog2 = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o novo preco do produto:"));
                    		ps = post.con.prepareStatement(sqlUpdate + ""+ objAtz +" = ?" +  script);
                    		ps.setDouble(1, dialog2);
                    	}
                    	ps.execute();
                    	ps.close();
                    	break;
                    case "delete":
                    	ps = post.con.prepareStatement("DELETE from produto_rotativo WHERE id_estoque =" +id+"");
                    	ps.execute();
                    	ps.close();
                    	JOptionPane.showMessageDialog(null, "Produto deletado de id " +id+ " deletado com sucesso!");
                    	break;
                    case "select":
                        String sql1 = "SELECT * FROM usuario ORDER BY nome_prod ASC";
                        ps = post.con.prepareStatement(sql1);
                        rs = ps.executeQuery();
                        
                        msgEstoque.append("================================\n")
                                  .append("       CONTROLE DE USUARIOS     \n")
                                  .append("================================\n\n");
                        
                        boolean temDados1 = false;
                        while (rs.next()) {
                            temDados1 = true;
                            msgEstoque.append("> Nome do usuario: ").append(rs.getString("nome")).append("\n")
                                      .append("  |-- ID do usuario: ").append(rs.getInt("id_user")).append("\n")
                                      .append("  |-- Senha: ").append(rs.getInt("senha")).append("\n")
                                      .append("  |-- Cargo: ").append(rs.getInt("cargo")).append("\n");
                        }
                        rs.close();
                        ps.close();
                        
                        if (temDados1) {
                            exibirPainelScroll("Estoque Rotativo", msgEstoque.toString(), 20, 34);
                        } else {
                            JOptionPane.showMessageDialog(null, "Estoque vazio.");
                        }
                        break;
                }
                post.con.close();
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro nas operacoes de Estoque: " + e.getMessage());
        }
    }
    
    public double calcularProd(double id) {
	    	double preco = 0;
	    	double encontrado = 0;
	    	if(post.conC()) {
		    	try {
		    		ps = post.con.prepareStatement("SELECT preco FROM produto_rotativo WHERE nome_prod = ?");
		    		rs = ps.executeQuery();
		    		while(rs.next()) {
		    			encontrado = rs.getDouble("preco");
		    		}
		    		preco = encontrado;
		    	}catch(NumberFormatException | SQLException e1) {
		    		
		    		return 0;
		    	}
	    	}
    	return (preco);
    }
    //financeiro
    public void aprovarPedido(int id, String status){
        try{
            if(post.conC()){
                StringBuilder mensagem1 = new StringBuilder();
                switch(status){
                    case "desaprovar":
                        ps = post.con.prepareStatement("UPDATE pedido SET aprovacao = false WHERE id_pedido = ?");
                        ps.setInt(1, id);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Status do pedido modificado para [PENDENTE].");
                        break;
                    case "aprovar":
                        ps = post.con.prepareStatement("UPDATE pedido SET aprovacao = true WHERE id_pedido = ?");
                        ps.setInt(1, id);
                        ps.execute();
                        ps.close();
                        JOptionPane.showMessageDialog(null, "Status do pedido modificado para [APROVADO].");
                        break;
                    
                }
                post.con.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void pesquisarPedido(int id, String status) {
    	try {
    		if(post.conC()) {
    			StringBuilder mensagem1 = new StringBuilder();
    			switch (status){
    			case "pesquisar":
                    ps = post.con.prepareStatement("SELECT * FROM pedido WHERE id_pedido = ?");
                    ps.setInt(1, id);
                    rs = ps.executeQuery();
                    
                    mensagem1.append("================================\n")
                             .append("       AUDITORIA DO PEDIDO              \n")
                             .append("================================\n\n");
                    
                    boolean existe = false;
                    if(rs.next()){
                        existe = true;
                        mensagem1.append("> ID PEDIDO: ").append(rs.getInt("id_pedido")).append("\n")
                                 .append(" |-- Vendedor: ").append(rs.getInt("vendedor")).append("\n")
                                 .append(" |-- Aprovacao Financeira: ").append(rs.getBoolean("aprovacao") ? "Aprovado" : "Pendente").append("\n")
                                 .append(" |-- Status de Entrega: ").append(rs.getBoolean("status_pedido") ? "Entregue" : "Em producao").append("\n")
                                 .append(" |-- ID Cliente: ").append(rs.getInt("cliente")).append("\n")
                                 .append(" |-- Data Emissao: ").append(rs.getDate("data_pedido")).append("\n")
                                 .append(" |-- Material: ").append(rs.getString("nomeMaterial")).append("\n")
                                 .append(String.format(" |-- Valor: R$ %.2f\n", rs.getDouble("valor_total")));
                    }
                    rs.close();
                    ps.close();
                    
                    if(existe) {
                        exibirPainelScroll("Auditoria", mensagem1.toString(), 15, 34);
                    } else {
                        JOptionPane.showMessageDialog(null, "Pedido nao localizado.");
                    }
                    break;
                case "visualizar todos":
                    ps = post.con.prepareStatement("SELECT * FROM pedido");
                    rs = ps.executeQuery();
                    
                    mensagem1.append("==============================\n")
                             .append("       LISTA DOS PEDIDOS              \n")
                             .append("==============================\n\n");
                    
                    boolean existe1 = false;
                    while(rs.next()){
                        existe1 = true;
                        mensagem1.append("> ID PEDIDO: ").append(rs.getInt("id_pedido")).append("\n")
                                 .append(" |-- Vendedor: ").append(rs.getInt("vendedor")).append("\n")
                                 .append(" |-- Aprovacao Financeira: ").append(rs.getBoolean("aprovacao") ? "Aprovado" : "Pendente").append("\n")
                                 .append(" |-- Status de Entrega: ").append(rs.getBoolean("status_pedido") ? "Entregue" : "Em producao").append("\n")
                                 .append(" |-- ID Cliente: ").append(rs.getInt("cliente")).append("\n")
                                 .append(" |-- Data Emissao: ").append(rs.getDate("data_pedido")).append("\n")
                                 .append(" |-- Material: ").append(rs.getString("nomeMaterial")).append("\n")
                                 .append(String.format(" |-- Valor: R$ %.2f\n", rs.getDouble("valor_total"))).append("\n")
                        		.append("------------------------------\n");
                    }
                    rs.close();
                    ps.close();
                    
                    if(existe1) {
                        exibirPainelScroll("Auditoria", mensagem1.toString(), 15, 34);
                    } else {
                        JOptionPane.showMessageDialog(null, "Pedido nao localizado.");
                    }
                    break;
    			}
    		}
	    } catch(SQLException | NumberFormatException e){
	        JOptionPane.showMessageDialog(null, e.getMessage());
	    }
    }
    private static void exibirPainelScroll(String titulo, String texto, int linhas, int colunas) {
        JTextArea textArea = new JTextArea(linhas, colunas);
        textArea.setText(texto);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); 
        
        // === ADICIONE ESTAS DUAS LINHAS AQUI ===
        textArea.setLineWrap(true);       // Ativa a quebra automática de linha
        textArea.setWrapStyleWord(true);   // Garante que a quebra ocorra entre palavras inteiras (sem cortar sílabas)
        // ======================================
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
