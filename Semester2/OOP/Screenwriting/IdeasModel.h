#pragma once
#include <QAbstractTableModel>
#include "Repository.h"

class IdeaModel : public QAbstractTableModel
{
private:
	Repository<Idea>* repo;

public:
	IdeaModel(Repository<Idea>* idea);

	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
	void addIdea(Idea& p);
	void modifyStatus(int row);
	void modifyDescr(int row, std::string descr);
	void clearData();
};