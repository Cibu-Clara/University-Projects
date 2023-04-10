using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Untold_windows_form_app
{
  public partial class Form1 : Form
  {
    private int selected_supervisor_id = -1;
    private int selected_volunteer_id = -1;
    public Form1()
    {
      InitializeComponent();
      PopulateSupervisorDataGridView();
    }
    
    private SqlConnection conn;
    private SqlCommand cmd;

    private void Form1_Load(object sender, EventArgs e)
    {
      conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      cmd = new SqlCommand();
      cmd.Connection = conn;
    }
    
    private void ClearData()
    {
      firstNameTextBox.Clear();
      lastNameTextBox.Clear();
      taskTextBox.Clear();
    }

    private void PopulateSupervisorDataGridView()
    {
      var selectSupervisors = "SELECT * FROM Supervisor";
      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      
      var dataAdapter = new SqlDataAdapter(selectSupervisors, conn);
      var dataSet = new DataSet();
      dataAdapter.Fill(dataSet);
      supervisorsDataGridView.ReadOnly = true;
      supervisorsDataGridView.DataSource = dataSet.Tables[0];
    }

    private void supervisorsDataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
    {
      ClearData();
      supervisorsDataGridView.CurrentRow.Selected = true;
      selected_supervisor_id = int.Parse(supervisorsDataGridView.Rows[e.RowIndex].Cells["id"].Value.ToString());

      supervisorIdTextBox.Text = selected_supervisor_id.ToString();

      if (supervisorsDataGridView.SelectedRows.Count > 0)
      {
        groupBox.Enabled = true;
      }

      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      conn.Open();
      var cmd = new SqlCommand("Select * from Volunteer where supervisor_id = @supervisor_id", conn);
      cmd.Parameters.AddWithValue("@supervisor_id", selected_supervisor_id);
      
      DataTable dt = new DataTable();
      var dataAdapter = new SqlDataAdapter(cmd);

      dataAdapter.Fill(dt);
      volunteersDataGridView.DataSource = dt;
      volunteersDataGridView.ReadOnly = true;
      cmd.ExecuteNonQuery();
      conn.Close();
    }

    private void addButton_Click(object sender, EventArgs e)
    {
      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      conn.Open();
      var cmd = new SqlCommand("insert into Volunteer(first_name, last_name, task, supervisor_id) values (@first_name, @last_name, @task, @supervisor_id)", conn);
            
      cmd.Parameters.AddWithValue("@first_name", firstNameTextBox.Text);
      cmd.Parameters.AddWithValue("@last_name", lastNameTextBox.Text);
      cmd.Parameters.AddWithValue("@task", taskTextBox.Text);
      cmd.Parameters.AddWithValue("@supervisor_id", int.Parse(supervisorIdTextBox.Text));
      cmd.ExecuteNonQuery();
      ClearData();
      populateVolunteerGridView();
      conn.Close();

      MessageBox.Show("Successfully inserted!");
    }

    private void populateVolunteerGridView()
    {
      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      conn.Open();
      var cmd = new SqlCommand("SELECT * FROM Volunteer WHERE supervisor_id = @supervisor_id", conn);
      cmd.Parameters.AddWithValue("@supervisor_id", selected_supervisor_id);

      DataTable dt = new DataTable();
      var dataAdapter = new SqlDataAdapter(cmd);

      dataAdapter.Fill(dt);
      volunteersDataGridView.DataSource = dt;
      volunteersDataGridView.ReadOnly = true;
      cmd.ExecuteNonQuery();
      conn.Close();
    }


    private void updateButton_Click(object sender, EventArgs e)
    {
      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      conn.Open();
      var cmd = new SqlCommand("update Volunteer set first_name=@first_name, last_name=@last_name, task=@task where id=@selected_volunteer_id", conn);
      cmd.Parameters.AddWithValue("@first_name", firstNameTextBox.Text);
      cmd.Parameters.AddWithValue("@last_name", lastNameTextBox.Text);
      cmd.Parameters.AddWithValue("@task", taskTextBox.Text);
      cmd.Parameters.AddWithValue("@selected_volunteer_id", selected_volunteer_id);

      cmd.ExecuteNonQuery();
      conn.Close();
      populateVolunteerGridView();
      ClearData();

      MessageBox.Show("Successfully updated!");
    }


    private void removeButton_Click(object sender, EventArgs e)
    {
      var conn = new SqlConnection("Data Source=localhost;Initial Catalog=UNTOLD;Integrated Security=True");
      conn.Open();
      var cmd = new SqlCommand("delete from Volunteer where id=@volunteer_id", conn);
      cmd.Parameters.AddWithValue("@volunteer_id", selected_volunteer_id);

      cmd.ExecuteNonQuery();
      conn.Close();
      ClearData();
      populateVolunteerGridView();

      MessageBox.Show("Successfully deleted!");
    }

    private void volunteersDataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
    {
      ClearData();
      volunteersDataGridView.CurrentRow.Selected = true;
      var volunteer_id = int.Parse(volunteersDataGridView.Rows[e.RowIndex].Cells["id"].Value.ToString());

      selected_volunteer_id = volunteer_id;

      firstNameTextBox.Text = volunteersDataGridView.Rows[e.RowIndex].Cells["first_name"].Value.ToString();
      lastNameTextBox.Text = volunteersDataGridView.Rows[e.RowIndex].Cells["last_name"].Value.ToString();
      taskTextBox.Text = volunteersDataGridView.Rows[e.RowIndex].Cells["task"].Value.ToString();
    }
  }
}
