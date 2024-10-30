package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Adm
 */
import conexao.conectaDAO;
import dto.ProdutosDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    private conectaDAO conexao;
    Connection conn;
    PreparedStatement st;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public ProdutosDAO() {

        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }

    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {

        try {
            st = conn.prepareStatement("INSERT INTO produtos(nome, valor, status) VALUES(?,?,?)");
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus());

            st.execute();

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        try {
            
            st = conn.prepareStatement("SELECT id, nome, valor, status FROM produtos");
            resultset = st.executeQuery();
            
            ArrayList<ProdutosDTO> listagem = new ArrayList<>();
            ProdutosDTO produtos = new ProdutosDTO();
            
            while (resultset.next()) {
                produtos.setId(resultset.getInt("id"));
                produtos.setNome(resultset.getString("nome"));
                produtos.setValor(resultset.getInt("valor"));
                produtos.setStatus(resultset.getString("status"));
                listagem.add(new ProdutosDTO(produtos));
            }
            
            return listagem;
        } catch (Exception ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }

    }
    
    public void venderProduto(int idProduto) throws SQLException {
        
        st = conn.prepareStatement("UPDATE produtos SET status = ? WHERE id = ?");
        st.setString(1, "Vendido");
        st.setInt(2, idProduto);
        
        st.execute();
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        try {
            
            st = conn.prepareStatement("SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'");
            resultset = st.executeQuery();
            
            ArrayList<ProdutosDTO> listagem = new ArrayList<>();
            ProdutosDTO produtos = new ProdutosDTO();
            
            while (resultset.next()) {
                produtos.setId(resultset.getInt("id"));
                produtos.setNome(resultset.getString("nome"));
                produtos.setValor(resultset.getInt("valor"));
                produtos.setStatus(resultset.getString("status"));
                listagem.add(new ProdutosDTO(produtos));
            }
            
            return listagem;
        } catch (Exception ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }

    }

}
