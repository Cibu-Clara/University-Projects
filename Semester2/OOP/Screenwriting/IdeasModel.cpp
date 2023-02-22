#include "IdeasModel.h"
#include<algorithm>
IdeaModel::IdeaModel(Repository<Idea>* idea) :
    repo{ idea }
{
}

int IdeaModel::rowCount(const QModelIndex& parent) const
{
    return this->repo->getSize();
}

int IdeaModel::columnCount(const QModelIndex& parent) const
{
    return 4;
}

QVariant IdeaModel::data(const QModelIndex& index, int role) const
{
    int row = index.row();
    int column = index.column();
    auto idea = this->repo->getAll()[row];
    if (role == Qt::DisplayRole) {
        switch (column) {
        case 0: return QString::fromStdString(idea.getDescription());
        case 1: return QString::fromStdString(idea.getStatus());
        case 2: return QString::fromStdString(idea.getCreator());
        case 3: return QString::number(idea.getAct());
        default: break;
        }
    }

    return QVariant();
}

QVariant IdeaModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (orientation == Qt::Horizontal && role == Qt::DisplayRole) {
        switch (section) {
        case 0: return QString{ "Description" };
        case 1: return QString{ "Status" };
        case 2: return QString{ "Creator" };
        case 3: return QString{ "Act" };
        default: break;
        }
    }

    return QVariant();
}

void IdeaModel::addIdea(Idea& p)
{
    int n = this->repo->getSize();

    this->beginInsertRows(QModelIndex{}, n, n);

    this->repo->addElement(p);

    this->endInsertRows();
}

void IdeaModel::modifyStatus(int row)
{
    this->repo->updateStatus(row);
    emit dataChanged(createIndex(0, 0), createIndex(this->repo->getSize() - 1, 3));
}

void IdeaModel::modifyDescr(int row, std::string descr)
{
    this->repo->updateDescription(row, descr);
    emit dataChanged(createIndex(0, 0), createIndex(this->repo->getSize() - 1, 3));
}

void IdeaModel::clearData()
{
    this->beginResetModel();

    this->repo->clear();

    this->endResetModel();
}
