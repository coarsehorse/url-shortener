db.createUser(
    {
        user: "us-root",
        pwd: "toortoor6",
        roles: [
            {
                role: "dbOwner",
                db: "url-shortener"
            }
        ]
    }
)
