namespace Untold_windows_form_app
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.supervisorsDataGridView = new System.Windows.Forms.DataGridView();
            this.supervisorsLabel = new System.Windows.Forms.Label();
            this.volunteersLabel = new System.Windows.Forms.Label();
            this.volunteersDataGridView = new System.Windows.Forms.DataGridView();
            this.firstNameLabel = new System.Windows.Forms.Label();
            this.lastNameLabel = new System.Windows.Forms.Label();
            this.taskLabel = new System.Windows.Forms.Label();
            this.firstNameTextBox = new System.Windows.Forms.TextBox();
            this.lastNameTextBox = new System.Windows.Forms.TextBox();
            this.taskTextBox = new System.Windows.Forms.TextBox();
            this.supervisorIdLabel = new System.Windows.Forms.Label();
            this.supervisorIdTextBox = new System.Windows.Forms.TextBox();
            this.addButton = new System.Windows.Forms.Button();
            this.removeButton = new System.Windows.Forms.Button();
            this.updateButton = new System.Windows.Forms.Button();
            this.groupBox = new System.Windows.Forms.GroupBox();
            ((System.ComponentModel.ISupportInitialize)(this.supervisorsDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.volunteersDataGridView)).BeginInit();
            this.groupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // supervisorsDataGridView
            // 
            this.supervisorsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.supervisorsDataGridView.Location = new System.Drawing.Point(12, 70);
            this.supervisorsDataGridView.Name = "supervisorsDataGridView";
            this.supervisorsDataGridView.RowTemplate.Height = 24;
            this.supervisorsDataGridView.Size = new System.Drawing.Size(794, 200);
            this.supervisorsDataGridView.TabIndex = 0;
            this.supervisorsDataGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.supervisorsDataGridView_CellClick);
            // 
            // supervisorsLabel
            // 
            this.supervisorsLabel.Font = new System.Drawing.Font("Microsoft YaHei UI", 22.2F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.supervisorsLabel.Location = new System.Drawing.Point(257, 9);
            this.supervisorsLabel.Name = "supervisorsLabel";
            this.supervisorsLabel.Size = new System.Drawing.Size(267, 46);
            this.supervisorsLabel.TabIndex = 1;
            this.supervisorsLabel.Text = "Supervisors:";
            // 
            // volunteersLabel
            // 
            this.volunteersLabel.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.volunteersLabel.Location = new System.Drawing.Point(12, 285);
            this.volunteersLabel.Name = "volunteersLabel";
            this.volunteersLabel.Size = new System.Drawing.Size(694, 34);
            this.volunteersLabel.TabIndex = 2;
            this.volunteersLabel.Text = "Volunteers managed by the selected supervisor:";
            // 
            // volunteersDataGridView
            // 
            this.volunteersDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.volunteersDataGridView.Location = new System.Drawing.Point(12, 322);
            this.volunteersDataGridView.Name = "volunteersDataGridView";
            this.volunteersDataGridView.RowTemplate.Height = 24;
            this.volunteersDataGridView.Size = new System.Drawing.Size(794, 214);
            this.volunteersDataGridView.TabIndex = 3;
            this.volunteersDataGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.volunteersDataGridView_CellClick);
            // 
            // firstNameLabel
            // 
            this.firstNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.firstNameLabel.Location = new System.Drawing.Point(16, 12);
            this.firstNameLabel.Name = "firstNameLabel";
            this.firstNameLabel.Size = new System.Drawing.Size(100, 23);
            this.firstNameLabel.TabIndex = 4;
            this.firstNameLabel.Text = "First Name:";
            // 
            // lastNameLabel
            // 
            this.lastNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lastNameLabel.Location = new System.Drawing.Point(16, 50);
            this.lastNameLabel.Name = "lastNameLabel";
            this.lastNameLabel.Size = new System.Drawing.Size(100, 23);
            this.lastNameLabel.TabIndex = 5;
            this.lastNameLabel.Text = "Last Name:";
            // 
            // taskLabel
            // 
            this.taskLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.taskLabel.Location = new System.Drawing.Point(16, 90);
            this.taskLabel.Name = "taskLabel";
            this.taskLabel.Size = new System.Drawing.Size(100, 23);
            this.taskLabel.TabIndex = 6;
            this.taskLabel.Text = "Task:";
            // 
            // firstNameTextBox
            // 
            this.firstNameTextBox.Location = new System.Drawing.Point(162, 12);
            this.firstNameTextBox.Name = "firstNameTextBox";
            this.firstNameTextBox.Size = new System.Drawing.Size(206, 22);
            this.firstNameTextBox.TabIndex = 7;
            // 
            // lastNameTextBox
            // 
            this.lastNameTextBox.Location = new System.Drawing.Point(162, 50);
            this.lastNameTextBox.Name = "lastNameTextBox";
            this.lastNameTextBox.Size = new System.Drawing.Size(206, 22);
            this.lastNameTextBox.TabIndex = 8;
            // 
            // taskTextBox
            // 
            this.taskTextBox.Location = new System.Drawing.Point(162, 90);
            this.taskTextBox.Name = "taskTextBox";
            this.taskTextBox.Size = new System.Drawing.Size(206, 22);
            this.taskTextBox.TabIndex = 9;
            // 
            // supervisorIdLabel
            // 
            this.supervisorIdLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.supervisorIdLabel.Location = new System.Drawing.Point(16, 131);
            this.supervisorIdLabel.Name = "supervisorIdLabel";
            this.supervisorIdLabel.Size = new System.Drawing.Size(119, 23);
            this.supervisorIdLabel.TabIndex = 10;
            this.supervisorIdLabel.Text = "Supervisor ID:";
            // 
            // supervisorIdTextBox
            // 
            this.supervisorIdTextBox.Location = new System.Drawing.Point(162, 131);
            this.supervisorIdTextBox.Name = "supervisorIdTextBox";
            this.supervisorIdTextBox.ReadOnly = true;
            this.supervisorIdTextBox.Size = new System.Drawing.Size(206, 22);
            this.supervisorIdTextBox.TabIndex = 11;
            // 
            // addButton
            // 
            this.addButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.addButton.Location = new System.Drawing.Point(453, 50);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(95, 31);
            this.addButton.TabIndex = 12;
            this.addButton.Text = "Add";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += new System.EventHandler(this.addButton_Click);
            // 
            // removeButton
            // 
            this.removeButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.removeButton.Location = new System.Drawing.Point(567, 50);
            this.removeButton.Name = "removeButton";
            this.removeButton.Size = new System.Drawing.Size(95, 31);
            this.removeButton.TabIndex = 13;
            this.removeButton.Text = "Remove";
            this.removeButton.UseVisualStyleBackColor = true;
            this.removeButton.Click += new System.EventHandler(this.removeButton_Click);
            // 
            // updateButton
            // 
            this.updateButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.updateButton.Location = new System.Drawing.Point(682, 50);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new System.Drawing.Size(95, 31);
            this.updateButton.TabIndex = 14;
            this.updateButton.Text = "Update";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += new System.EventHandler(this.updateButton_Click);
            // 
            // groupBox
            // 
            this.groupBox.Controls.Add(this.updateButton);
            this.groupBox.Controls.Add(this.removeButton);
            this.groupBox.Controls.Add(this.addButton);
            this.groupBox.Controls.Add(this.supervisorIdTextBox);
            this.groupBox.Controls.Add(this.supervisorIdLabel);
            this.groupBox.Controls.Add(this.taskTextBox);
            this.groupBox.Controls.Add(this.lastNameTextBox);
            this.groupBox.Controls.Add(this.firstNameTextBox);
            this.groupBox.Controls.Add(this.taskLabel);
            this.groupBox.Controls.Add(this.lastNameLabel);
            this.groupBox.Controls.Add(this.firstNameLabel);
            this.groupBox.Enabled = false;
            this.groupBox.Location = new System.Drawing.Point(12, 553);
            this.groupBox.Name = "groupBox";
            this.groupBox.Size = new System.Drawing.Size(793, 168);
            this.groupBox.TabIndex = 15;
            this.groupBox.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(817, 730);
            this.Controls.Add(this.groupBox);
            this.Controls.Add(this.volunteersDataGridView);
            this.Controls.Add(this.volunteersLabel);
            this.Controls.Add(this.supervisorsLabel);
            this.Controls.Add(this.supervisorsDataGridView);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.supervisorsDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.volunteersDataGridView)).EndInit();
            this.groupBox.ResumeLayout(false);
            this.groupBox.PerformLayout();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.GroupBox groupBox;

        private System.Windows.Forms.Button removeButton;
        private System.Windows.Forms.Button updateButton;

        private System.Windows.Forms.TextBox lastNameTextBox;
        private System.Windows.Forms.TextBox taskTextBox;
        private System.Windows.Forms.Label supervisorIdLabel;
        private System.Windows.Forms.Button addButton;

        private System.Windows.Forms.TextBox firstNameTextBox;
        private System.Windows.Forms.TextBox supervisorIdTextBox;

        private System.Windows.Forms.Label taskLabel;

        private System.Windows.Forms.Label lastNameLabel;

        private System.Windows.Forms.Label firstNameLabel;

        private System.Windows.Forms.DataGridView volunteersDataGridView;

        private System.Windows.Forms.Label volunteersLabel;

        private System.Windows.Forms.Label supervisorsLabel;

        private System.Windows.Forms.DataGridView supervisorsDataGridView;

        #endregion
    }
}