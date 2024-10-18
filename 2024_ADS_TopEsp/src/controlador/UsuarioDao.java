package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

/**
 * Todo DAO faz as transações de BD. Este DAO é do Usuario. Logo, fará apenas
 * transações com a tabela USUÁRIO.
 *
 * @author Marcelo Rafael Borth
 */
public class UsuarioDao {

    public void inserir(Usuario u) throws Exception {
        //Insere no BD
        String sql = "insert into usuario (nome, email, senha)"
                + "values (?, ?, ?)";

        try {
            //Conecta no BD
            java.sql.Connection conexao = Conexao.getConexao();
            //Prepara a instrução SQL
            java.sql.PreparedStatement ps = conexao.prepareStatement(sql);

            //Seta na SQL os atributos do usuário
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());

            //Executa a SQL no BD
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Usuario> buscar(String nome) throws Exception {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from usuario ";
        sql += (!nome.equals("")) ? " where nome like ?" : "";
        java.sql.Connection conexao = Conexao.getConexao();
        
        //try-with-resources fecha o recurso automaticamente
        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (!nome.equals("")) {
                ps.setString(1, "%" + nome + "%");
            }
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { // Percorre os usuário da consulta do BD
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    
                    lista.add(u);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
    public void excluir(Integer id) throws Exception {
        String sql = "delete from usuario where id = ?";
        
        java.sql.Connection conexao = Conexao.getConexao();
        
        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
