// cf. https://www.baeldung.com/sql-injection

package sql.injection;

import com.biz.org.AccountDTO;
import com.biz.org.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SqlExample {
    public void staticQuery() throws SQLException {
        Connection c = DB.getConnection();
        // ok:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM happy_messages");
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void getAllFields(@RequestParam String tableName) throws SQLException {
        Connection c = DB.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM " + tableName);
    }

    // No ResponseBody or RequestParam
    public void getAllFields2(String tableName) throws SQLException {
        Connection c = DB.getConnection();
        // ok:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM " + tableName);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void getAllFields3(String tableName) throws SQLException {
        Connection c = DB.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM " + tableName);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void findAccountsById(@RequestParam String id) throws SQLException {
        String sql = "SELECT * "
                + "FROM accounts WHERE id = '"
                + id
                + "'";
        Connection c = DB.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery(sql);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void findAccountsById(@RequestParam String id, @RequestParam String field) throws SQLException {
        String sql = "SELECT ";
        sql += field;
        sql += " FROM accounts WHERE id = '";
        sql += id;
        sql += "'";
        Connection c = DB.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().executeQuery(sql);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void findAccountsByIdPrepare(@RequestParam String id, @RequestParam String field) throws SQLException {
        String sql = "SELECT ";
        sql += field;
        sql += " FROM accounts WHERE id = '";
        sql += id;
        sql += "'";
        Connection c = DB.getConnection();

        // ruleid:formatted-sql-string-deepsemgrep
        c.prepareStatement(sql).execute();

        // ruleid:formatted-sql-string-deepsemgrep
        c.prepareCall(sql).execute();
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void findAccountsByIdPrepareColumn(@RequestParam String id, @RequestParam String field) throws SQLException {
        String sql = "SELECT foo FROM accounts WHERE id='1234'";
        Connection c = DB.getConnection();

        // SQL statement is tainted
        // ruleid:formatted-sql-string-deepsemgrep
        c.prepareStatement(field, new string[]{ "foo" }).execute();

        // SQL statement isn't tainted
        // ok:formatted-sql-string-deepsemgrep
        c.prepareStatement(sql, new string[]{field}).execute();
    }
}

public class SqlExample2 {
    public void getAllFields(HttpServletRequest req) throws SQLException {
        String tableName = req.getParameter("tableName");
        Connection c = db.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute("SELECT * FROM " + tableName);
    }

    public void findAccountsById(HttpServletRequest req) throws SQLException {
        String id = req.getParameter("id");
        String sql = "SELECT * "
                + "FROM accounts WHERE id = '"
                + id
                + "'";
        Connection c = db.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(sql);
    }

    public List<AccountDTO> findAccountsById(HttpServletRequest req) {
        String id = req.getParameter("id");
        String jql = "from Account where id = '" + id + "'";
        EntityManager em = emfactory.createEntityManager();
        // ruleid:formatted-sql-string-deepsemgrep
        TypedQuery<Account> q = em.createQuery(jql, Account.class);
        return q.getResultList()
                .stream()
                .map(this::toAccountDTO)
                .collect(Collectors.toList());
    }
}

public class SQLExample3 {
    @GetMapping("/api/foos")
    @ResponseBody
    public void getAllFields(@RequestParam(required=true) String tableName) throws SQLException {
        Connection c = db.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(String.format("SELECT * FROM %s", tableName);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void findAccountsById(@RequestParam(required=false) String id) throws SQLException {
        String sql = String.format("SELECT * FROM accounts WHERE id = '%s'", id);
        Connection c = db.getConnection();
        // ruleid:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(sql);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public List<AccountDTO> findAccountsById(@RequestParam(required=true) String id) {
        String jql = String.format("from Account where id = '%s'", id);
        EntityManager em = emfactory.createEntityManager();
        // ruleid: formatted-sql-string-deepsemgrep
        TypedQuery<Account> q = em.createQuery(jql, Account.class);
        return q.getResultList()
                .stream()
                .map(this::toAccountDTO)
                .collect(Collectors.toList());
    }

    public void findAccountsByIdOk() throws SQLException {
        String id = "const"
        String sql = String.format("SELECT * FROM accounts WHERE id = '%s'", id);
        Connection c = db.getConnection();
        // ok:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(sql);
    }

}

public class tableConcatStatements {
    public void tableConcat() {
        // ok:formatted-sql-string-deepsemgrep
        stmt.execute("DROP TABLE " + tableName);
        stmt.execute(String.format("CREATE TABLE %s", tableName));
    }
}

// This whole operation has nothing to do with SQL
public class FalsePositiveCase {
    private ApiClient apiClient; // imagine an ApiClient class that contains a method named execute

    public void test(String parameter) throws ApiException {
        com.squareup.okhttp.Call call = constructHttpCall(parameter); // Create OKHttp call using parameter from outside
        // ok: formatted-sql-string-deepsemgrep
        apiClient.execute(call);
        // ok: formatted-sql-string-deepsemgrep
        apiClient.execute(call);
        apiClient.run(call); // proof that 'execute' name is causing the false-positive
    }


    @GetMapping("/api/foos")
    @ResponseBody
    public void ok1(@RequestParam(required=false) Boolean id) throws SQLException {
        String sql = String.format("SELECT * FROM accounts WHERE id = '%s'", id);
        Connection c = db.getConnection();
        // ok:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(sql);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void ok2(@RequestParam(required=false) String id) throws SQLException {
        String sql = String.format("SELECT * FROM accounts WHERE id = '%s'", (id != null));
        Connection c = db.getConnection();
        // ok:formatted-sql-string-deepsemgrep
        ResultSet rs = c.createStatement().execute(sql);
    }
}


