using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using System.Configuration;

namespace Master_detail_windows_form
{
    public partial class Form1 : Form
    {
        private readonly DataSet _dataSet = new DataSet();
        private SqlConnection _connection;

        private SqlDataAdapter _childAdapter, _parentAdapter;
        private readonly BindingSource _bindingSourceParent = new BindingSource();
        private readonly BindingSource _bindingSourceChild = new BindingSource();
        
        public Form1()
        {
            InitializeComponent();
            InitDatabase();
            InitUi();
        }

        private void InitDatabase()
        {
            String connectionString = ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString;
            String database = ConfigurationManager.AppSettings["Database"];

            this._connection = new SqlConnection(String.Format(connectionString, database));
            this._parentAdapter = new SqlDataAdapter(ConfigurationManager.AppSettings["SelectParent"], _connection);
            this._childAdapter = new SqlDataAdapter(ConfigurationManager.AppSettings["SelectChild"], _connection);

            new SqlCommandBuilder(this._childAdapter);
            new SqlCommandBuilder(this._parentAdapter).GetInsertCommand();

            this._parentAdapter.Fill(this._dataSet, ConfigurationManager.AppSettings["ParentTableName"]);
            this._childAdapter.Fill(this._dataSet, ConfigurationManager.AppSettings["ChildTableName"]);

            DataColumn parentDataColumn = this._dataSet.Tables[ConfigurationManager.AppSettings["ParentTableName"]].Columns[ConfigurationManager.AppSettings["ParentReferencedKey"]];
            DataColumn childDataColumn = this._dataSet.Tables[ConfigurationManager.AppSettings["ChildTableName"]].Columns[ConfigurationManager.AppSettings["ChildForeignKey"]];

            String relationName = ConfigurationManager.AppSettings["ForeignKey"];

            var dataRelation = new DataRelation(
                relationName,
                parentDataColumn,
                childDataColumn
            );

            this._dataSet.Relations.Add(dataRelation);
        }

        private void InitUi()
        {
            _bindingSourceParent.DataSource = _dataSet;
            _bindingSourceParent.DataMember = ConfigurationManager.AppSettings["ParentTableName"];

            _bindingSourceChild.DataSource = _bindingSourceParent;
            _bindingSourceChild.DataMember = ConfigurationManager.AppSettings["ForeignKey"];

            dataGridViewParent.DataSource = _bindingSourceParent;
            dataGridViewChild.DataSource = _bindingSourceChild;
        }

        private void refresh_button_Click(object sender, EventArgs e)
        {
            _dataSet.Tables[ConfigurationManager.AppSettings["ChildTableName"]].Clear();
            _dataSet.Tables[ConfigurationManager.AppSettings["ParentTableName"]].Clear();
            
            _parentAdapter.Fill(_dataSet, ConfigurationManager.AppSettings["ParentTableName"]);
            _childAdapter.Fill(_dataSet, ConfigurationManager.AppSettings["ChildTableName"]);
        }

        private void update_button_Click(object sender, EventArgs e)
        {
            _childAdapter.Update(_dataSet, ConfigurationManager.AppSettings["ChildTableName"]);
            _parentAdapter.Update(_dataSet, ConfigurationManager.AppSettings["ParentTableName"]);
        }


        private void label2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }
    }
}