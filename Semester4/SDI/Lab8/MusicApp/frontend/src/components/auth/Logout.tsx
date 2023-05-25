import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export const LogoutFrom = () => {
    const navigate = useNavigate();

    useEffect(() => {
        localStorage.removeItem("token");
        localStorage.removeItem("user_id");
        localStorage.removeItem("user");
        navigate(`/`);
      }, []);

    return (<></>);
}