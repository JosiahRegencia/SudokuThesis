class Column;

class Node
{

  protected:
	Node *up, *down, *left, *right;
	Column * col;

  public:
	Node() : up(this), down(this), left(this), right(this) {}
	void unlink();
	void relink();
	void insertAfter(Node *q);
	void insertBelow(Node *q);
};


void Node::unlink()
{
	up->down = down;
	down->up = up;
}

void Node::relink()
{
	up->down = this;
	down->up = this;
}

void Node::insertAfter(Node *q)
{
	q->left = this;
	q->right = right;
	q->left->right = q;
	q->right->left = q;
}

void Node::insertBelow(Node *q)
{
	q->up = this;
	q->down = down;
	q->up->down = q;
	q->down->up = q;
}

